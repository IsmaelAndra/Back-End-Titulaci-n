package Proyecto.Titulacion.Events.Event;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import Proyecto.Titulacion.User.User.User;
import Proyecto.Titulacion.User.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;

@RestController
@RequestMapping("/api/event")
@CrossOrigin({ "*" })
@Tag(name = "Controller Event (eventos)", description = "Table Event")
public class EventController {

    @Autowired
    UserService userService;

    @Autowired
    EventService service;

    private static final String UPLOAD_DIR = "uploads/";

    @Operation(summary = "Gets an event for your idEvent")
    @GetMapping("/{idEvent}/")
    public ResponseEntity<Event> getEventEntity(@PathVariable Long idEvent) {
        Optional<Event> event = service.findById(idEvent);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all events")
    @GetMapping("/")
    public List<Event> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an event, requires hasAnyRole(ADMIN)")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Event> saveEvent(
            @RequestParam("titleEvent") String titleEvent,
            @RequestParam("descriptionEvent") String descriptionEvent,
            @RequestParam("dateEvent") LocalDate dateEvent,
            @RequestParam("hourEvent") LocalTime hourEvent,
            @RequestParam("placeEvent") String placeEvent,
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        Event event = new Event();
        event.setTitleEvent(titleEvent);
        event.setDescriptionEvent(descriptionEvent);
        event.setDateEvent(dateEvent);
        event.setHourEvent(hourEvent);
        event.setPlaceEvent(placeEvent);
        event.setUser(user);

        if (!image.isEmpty()) {
            String imageUrl = saveImage(image);
            event.setUrl_imageEvent(imageUrl);
        }

        Event savEvent = service.save(event);

        return ResponseEntity.ok(savEvent);
    }

    @Operation(summary = "Updates an event by its idEvent, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Event> updateEvent(
            @PathVariable long idEvent,
            @RequestParam("titleEvent") String titleEvent,
            @RequestParam("descriptionEvent") String descriptionEvent,
            @RequestParam("dateEvent") LocalDate dateEvent,
            @RequestParam("hourEvent") LocalTime hourEvent,
            @RequestParam("placeEvent") String placeEvent,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Optional<Event> optionalEvent = service.findById(idEvent);
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            existingEvent.setTitleEvent(titleEvent);
            existingEvent.setDescriptionEvent(descriptionEvent);
            existingEvent.setDateEvent(dateEvent);
            existingEvent.setHourEvent(hourEvent);
            existingEvent.setPlaceEvent(placeEvent);
            service.save(existingEvent);

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                existingEvent.setUrl_imageEvent(imageUrl);
            }

            Event updEvent = service.save(existingEvent);
            return ResponseEntity.ok(updEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Removes an event by its idEvent, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable long idEvent) {
        service.deleteById(idEvent);
    }

    @Operation(summary = "Partial updates an events by its idEvent, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Event> partialUpdate(@PathVariable long idEvent, @RequestBody Map<String, Object> fields) {

        Optional<Event> optionalEvent = service.findById(idEvent);

        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Event entity = optionalEvent.get();

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Event.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Event", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }

        Event updateEvent = service.save(entity);
        return ResponseEntity.ok(updateEvent);
    }

    @Operation(summary = "Event Pagination")
    @GetMapping("/paginated")
    public Page<Event> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEvent") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Event")
    @GetMapping("/search")
    public Page<Event> findByTitleEvent(
            @RequestParam String titleEvent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEvent") String sortBy) {
        return service.findByTitleEvent(titleEvent, page, size, sortBy);
    }

    @Operation(summary = "Daily, monthly and yearly event statistics.")
    @GetMapping("/stats")
    public Map<String, Long> getEventStats(@RequestParam String period) {
        return service.getEventStats(period);
    }

    private String saveImage(MultipartFile image) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path copyLocation = Paths.get(uploadPath + File.separator + fileName);
            Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return "uploads/" + fileName; // Guarda la ruta relativa
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo de imagen");
        }
    }
}

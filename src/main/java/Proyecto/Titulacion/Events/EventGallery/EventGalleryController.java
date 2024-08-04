package Proyecto.Titulacion.Events.EventGallery;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/eventGallery")
@CrossOrigin({ "*" })
@Tag(name = "Controller Event Gallery (Galeria de Eventos)", description = "Tabla Event Gallery")
public class EventGalleryController {

    @Autowired
    EventGalleryService service;

    @Operation(summary = "Gets an event gallery for your idEventGallery")
    @GetMapping("/{idEventGallery}/")
    public EventGallery findById(@PathVariable long idEventGallery) {
        return service.findById(idEventGallery);
    }

    @Operation(summary = "Gets all events gallery")
    @GetMapping("/")
    public List<EventGallery> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an event gallery, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EventGallery save(@RequestBody EventGallery entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates an event gallery by its idEventGallery, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idEventGallery}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EventGallery update(@RequestBody EventGallery entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an event gallery by its idEventGallery, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idEventGallery}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable long idEventGallery) {
        service.deleteById(idEventGallery);
    }

    @Operation(summary = "Partial updates an event gallery by its idEventGallery, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idEventGallery}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EventGallery partialUpdate(@PathVariable long idEventGallery, @RequestBody Map<String, Object> fields) {

        EventGallery entity = findById(idEventGallery);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = EventGallery.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad EventGallery", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

    @Operation(summary = "Event Gallery Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<EventGallery> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEventGallery") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Event Gallery")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<EventGallery> findByTitleEventGallery(
            @RequestParam String titleEventGallery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id_event") String sortBy) {
        return service.findByTitleEventGallery(titleEventGallery, page, size, sortBy);
    }
}

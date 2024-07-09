package Proyecto.Titulacion.Events.Event;

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
@RequestMapping("/api/event")
@CrossOrigin({"*"})
@Tag(name = "Controller Event (eventos)", description = "Table event")
public class EventController {

    @Autowired
    EventService service;

    @Operation(summary = "gets an event for your idEvent, requires hasAnyRole")
    @GetMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Event findById( @PathVariable long idEvent ){
        return service.findById(idEvent);
    }

    @Operation(summary = "Gets all events, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<Event> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an event, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Event save( @RequestBody Event entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an event by its idEvent, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Event update ( @RequestBody Event entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an event by its idEvent, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idEvent ){
        service.deleteById(idEvent);
    }

    @Operation(summary = "partial updates an events by its idEvent, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idEvent}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Event partialUpdate(@PathVariable long idEvent, @RequestBody Map<String, Object> fields){

        Event entity = findById(idEvent);

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
        return update(entity);
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Event> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEvent") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Event> findByTitleEvent(
            @RequestParam String titleEvent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEvent") String sortBy) {
        return service.findByTitleEvent(titleEvent, page, size, sortBy);
    }
}

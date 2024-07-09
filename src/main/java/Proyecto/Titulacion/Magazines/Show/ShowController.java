package Proyecto.Titulacion.Magazines.Show;

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
@RequestMapping("/api/show")
@CrossOrigin({ "*" })
@Tag(name = "Controller Show", description = "Table show")
public class ShowController {
    @Autowired
    ShowService service;

    @Operation(summary = "Gets an show for your idShow, requires hasAnyRole")
    @GetMapping("/{idShow}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Show findById(@PathVariable long idShow) {
        return service.findById(idShow);
    }

    @Operation(summary = "Gets all shows, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<Show> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an show, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Show save(@RequestBody Show entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates an show by its idShow, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idShow}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Show update(@RequestBody Show entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an show by its idShow, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idShow}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable long idShow) {
        service.deleteById(idShow);
    }

    @Operation(summary = "Partial updates an shows by its idShow, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idShow}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Show partialUpdate(@PathVariable long idShow, @RequestBody Map<String, Object> fields) {

        Show entity = findById(idShow);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Show.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad Show", ex);
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
    public Page<Show> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idShow") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Show> findByTitleShow(
            @RequestParam String titleShow,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idShow") String sortBy) {
        return service.findByTitleShow(titleShow, page, size, sortBy);
    }
}

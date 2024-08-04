package Proyecto.Titulacion.Achievenment;

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
@RequestMapping("/api/achievenment")
@CrossOrigin({ "*" })
@Tag(name = "Controller Achievenment (Logros)", description = "Table Achievements")
public class AchievenmentController {
    @Autowired
    AchievenmentService service;

    @Operation(summary = "Gets an achievement for your idAchievement")
    @GetMapping("/{idAchievement}/")
    public Achievenment findById(@PathVariable long idAchievement) {
        return service.findById(idAchievement);
    }

    @Operation(summary = "Gets all achievements")
    @GetMapping("/")
    public List<Achievenment> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an achievement, requires hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public Achievenment save(@RequestBody Achievenment entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates an achievement by its idAchievement, requires hasAnyRole")
    @PutMapping("/{idAchievement}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public Achievenment update(@RequestBody Achievenment entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an achievement by its idAchievement, requires hasAnyRole")
    @DeleteMapping("/{idAchievement}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idAchievement) {
        service.deleteById(idAchievement);
    }

    @Operation(summary = "Partial update an achievement by its idAchievement, requires hasAnyRole")
    @PatchMapping("/{idAchievement}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public Achievenment partialUpdate(@PathVariable long idAchievement, @RequestBody Map<String, Object> fields) {

        Achievenment entity = findById(idAchievement);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Achievenment.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Achievenment", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

    @Operation(summary = "Achievement Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Achievenment> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idAchievement") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for achievements")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Achievenment> findByNameAchievement(
            @RequestParam String nameAchievement,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idAchievement") String sortBy) {
        return service.findByNameAchievement(nameAchievement, page, size, sortBy);
    }
}

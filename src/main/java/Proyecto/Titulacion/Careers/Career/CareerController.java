package Proyecto.Titulacion.Careers.Career;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/career")
@CrossOrigin({"*"})
@Tag(name = "Controller Careers (Carreras)", description = "Tabla Career")
public class CareerController {
    @Autowired
    CareerService service;

    @Operation(summary = "Gets an career for your idCareer")
    @GetMapping("/{idCareer}/")
    public ResponseEntity<Career> findById(@PathVariable long idCareer) {
        Optional<Career> career = service.findById(idCareer);
        return career.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all careers")
    @GetMapping("/")
    public List<Career> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an career, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Career save(@RequestBody Career entity) {
        return service.save(entity);
    }

    @Operation(summary = "Updates an career by its idCareer, requires hasAnyRole(ADMIN)")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Career update(@RequestBody Career entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an career by its idCareer, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idCareer}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable long idCareer) {
        if (service.findById(idCareer).isPresent()) {
            service.deleteById(idCareer);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Partial updates an career by its idCareer, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idCareer}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Career> partialUpdate(@PathVariable long idCareer, @RequestBody Map<String, Object> fields) {

        Optional<Career> optionalCareer = service.findById(idCareer);
        if (!optionalCareer.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Career entity = optionalCareer.get();

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Career.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Career", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return ResponseEntity.ok(service.save(entity));
    }

    @Operation(summary = "Career Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Career> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCareer") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Career")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Career> findByNameCareer(
            @RequestParam String nameCareer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCareer") String sortBy) {
        return service.findByNameCareer(nameCareer, page, size, sortBy);
    }
}

package Proyecto.Titulacion.Projects.ProjectImage;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/proyectImage")
@CrossOrigin({ "*" })
@Tag(name = "Controller Proyect Image (Imagen del Proyecto)", description = "Table Proyect Image")
public class ProjectImageController {
    @Autowired
    ProjectImageService service;

    @Operation(summary = "Gets a Proyect Image for your idProjectImage")
    @GetMapping("/{idProjectImage}/")
    public ResponseEntity<ProjectImage> findById(@PathVariable long idProjectImage) {
        ProjectImage projectImage = service.findById(idProjectImage);
        if (projectImage != null) {
            return ResponseEntity.ok(projectImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Gets all products")
    @GetMapping("/")
    public ResponseEntity<List<ProjectImage>> findAll() {
        List<ProjectImage> projectImages = service.findAll();
        return ResponseEntity.ok(projectImages);
    }

    @Operation(summary = "Save a Proyect Image with images, requires hasAnyRole(EMPRENDEDOR)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<ProjectImage> save(@RequestBody ProjectImage entity) {
        ProjectImage savedProjectImage = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectImage);
    }

    @Operation(summary = "Updates a Proyect Image by its idProjectImage, requires hasAnyRole(EMPRENDEDOR)")
    @PutMapping("/{idProjectImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<ProjectImage> update(@RequestBody ProjectImage entity) {
        ProjectImage updatedProjectImage = service.save(entity);
        return ResponseEntity.ok(updatedProjectImage);
    }

    @Operation(summary = "Removes a Proyect Image by its idProjectImage, requires hasAnyRole(ADMIN, EMPRENDEDOR)")
    @DeleteMapping("/{idProjectImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Void> deleteById(@PathVariable long idProjectImage) {
        service.deleteById(idProjectImage);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Partial updates a Proyect Image by its idProjectImage, requires hasAnyRole(EMPRENDEDOR)")
    @PatchMapping("/{idProjectImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<ProjectImage> partialUpdate(@PathVariable long idProjectImage,
            @RequestBody Map<String, Object> fields) {
        ProjectImage entity = service.findById(idProjectImage);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            try {
                Field campoEntidad = ProjectImage.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad ProjectImage",
                        ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }

        return ResponseEntity.ok(service.save(entity));
    }
}

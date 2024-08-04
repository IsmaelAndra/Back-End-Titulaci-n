package Proyecto.Titulacion.Projects.StudentProject;

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
@RequestMapping("/api/studentProyect")
@CrossOrigin({ "*" })
@Tag(name = "Controller Student Project (Proyecto de Estudiante)", description = "Table Student Project")
public class StudentProjectController {
    @Autowired
    StudentProjectService service;

    @Operation(summary = "Gets a Student Project for your idStudentProject")
    @GetMapping("/{idStudentProject}/")
    public StudentProject findById(@PathVariable long idStudentProject) {
        return service.findById(idStudentProject);
    }

    @Operation(summary = "Gets all products")
    @GetMapping("/")
    public List<StudentProject> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save a Student Project with images, requires hasAnyRole(EMPRENDEDOR)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public StudentProject save(@RequestBody StudentProject entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates a Student Project by its idStudentProject, requires hasAnyRole(EMPRENDEDOR)")
    @PutMapping("/{idStudentProject}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public StudentProject update(@RequestBody StudentProject entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes a Student Project by its idStudentProject, requires hasAnyRole(ADMIN, EMPRENDEDOR)")
    @DeleteMapping("/{idStudentProject}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idStudentProject) {
        service.deleteById(idStudentProject);
    }

    @Operation(summary = "Partial updates a Student Project by its idStudentProject, requires hasAnyRole(EMPRENDEDOR)")
    @PatchMapping("/{idStudentProject}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public StudentProject partialUpdate(@PathVariable long idStudentProject, @RequestBody Map<String, Object> fields) {
        StudentProject entity = findById(idStudentProject);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = StudentProject.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad StudentProject", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

    @Operation(summary = "Student Project Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<StudentProject> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudentProject") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Student Project")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<StudentProject> findByNameStudentProject(
            @RequestParam String nameStudentProject,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudentProject") String sortBy) {
        return service.findByNameStudentProject(nameStudentProject, page, size, sortBy);
    }
}

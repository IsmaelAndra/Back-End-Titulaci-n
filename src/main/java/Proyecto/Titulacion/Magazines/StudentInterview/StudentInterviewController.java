package Proyecto.Titulacion.Magazines.StudentInterview;

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
@RequestMapping("/api/studentInterview")
@CrossOrigin({"*"})
@Tag(name = "Controller student interview (entrevista estudiantil)", description = "Tabla studentInterview")
public class StudentInterviewController {

    @Autowired
    StudentInterviewService service;

    @Operation(summary = "Gets an student interview for your idStudentInterview, requires hasAnyRole")
    @GetMapping("/{idStudentInterview}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public StudentInterview findById( @PathVariable long idStudentInterview ){
        return service.findById(idStudentInterview);
    }

    @Operation(summary = "Gets all student interview, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<StudentInterview> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an student interview, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudentInterview save( @RequestBody StudentInterview entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an student interview by its idStudentInterview, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idStudentInterview}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudentInterview update ( @RequestBody StudentInterview entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an studdent interview by its idStudentInterview, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idStudentInterview}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idStudentInterview ){
        service.deleteById(idStudentInterview);
    }

    @Operation(summary = "partial updates an student interview by its idStudentInterview, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idStudentInterview}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public StudentInterview partialUpdate(@PathVariable long idStudentInterview, @RequestBody Map<String, Object> fields){

        StudentInterview entity = findById(idStudentInterview);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = StudentInterview.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad StudentInterview", ex);
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
    public Page<StudentInterview> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudentInterview") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<StudentInterview> findByNameStudentInterview(
            @RequestParam String nameStudentInterview,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idStudentInterview") String sortBy) {
        return service.findByNameStudentInterview(nameStudentInterview, page, size, sortBy);
    }
}

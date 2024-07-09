package Proyecto.Titulacion.Careers.AcademicProgram;

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
@RequestMapping("/api/academicProgram")
@CrossOrigin({"*"})
@Tag(name = "Controller of Program academic (programas academicos)", description = "Table program academic")
public class AcademicProgramController {

    @Autowired
    AcademicProgramService service;

    @Operation(summary = "gets an program academic for your idAcademicProgram, requires hasAnyRole")
    @GetMapping("/{idAcademicProgram}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public AcademicProgram findById( @PathVariable long idAcademicProgram ){
        return service.findById(idAcademicProgram);
    }

    @Operation(summary = "Gets all programs academics, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<AcademicProgram> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an academic program, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AcademicProgram save( @RequestBody AcademicProgram entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an academic program by its idAcademicProgram, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idAcademicProgram}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AcademicProgram update ( @RequestBody AcademicProgram entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an academic program by its idAcademicProgram, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idAcademicProgram}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idAcademicProgram ){
        service.deleteById(idAcademicProgram);
    }

    @Operation(summary = "partial updates an academic program by its idAcademicProgram, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idAcademicProgram}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AcademicProgram partialUpdate(@PathVariable long idAcademicProgram, @RequestBody Map<String, Object> fields){

        AcademicProgram entity = findById(idAcademicProgram);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = AcademicProgram.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad AcademicProgram", ex);
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
    public Page<AcademicProgram> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idAcademicProgram") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<AcademicProgram> findByNameAcademicProgram(
            @RequestParam String nameAcademicProgram,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idAcademicProgram") String sortBy) {
        return service.findByNameAcademicProgram(nameAcademicProgram, page, size, sortBy);
    }
}

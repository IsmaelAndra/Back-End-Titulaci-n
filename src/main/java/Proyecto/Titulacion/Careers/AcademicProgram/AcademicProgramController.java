package Proyecto.Titulacion.Careers.AcademicProgram;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/academicProgram")
@CrossOrigin({"*"})
@Tag(name = "Controller of Program academic (programas academicos)", description = "Table program academic")
public class AcademicProgramController {

    @Autowired
    AcademicProgramService service;

    @Operation(summary = "gets an program academic for your id, requires hasAnyRole")
    @GetMapping("/{id}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public AcademicProgram findById( @PathVariable long id ){
        return service.findById(id);
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
    
    @Operation(summary = "updates an academic program by its id, requires hasAnyRole(ADMIN)")
    @PutMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AcademicProgram update ( @RequestBody AcademicProgram entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an academic program by its id, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long id ){
        service.deleteById(id);
    }

    @Operation(summary = "partial updates an academic program by its id, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AcademicProgram partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields){

        AcademicProgram entity = findById(id);

        // itera sobre los campos que se desean actualizar
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            // utiliza reflection para establecer el valor del campo en la entidad
            try {
                Field campoEntidad = AcademicProgram.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // maneja la excepción si ocurre algún error al acceder al campo
            }
        }
        return update(entity);
    }

}

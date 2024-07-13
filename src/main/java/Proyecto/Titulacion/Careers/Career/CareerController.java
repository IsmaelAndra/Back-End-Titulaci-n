package Proyecto.Titulacion.Careers.Career;

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
@RequestMapping("/api/career")
@CrossOrigin({"*"})
@Tag(name = "Controller of careers", description = "Tabla career")
public class CareerController {

    @Autowired
    CareerService service;

    @Operation(summary = "gets an career for your idCareer, requires hasAnyRole")
    @GetMapping("/{idCareer}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Career findById( @PathVariable long idCareer ){
        return service.findById(idCareer);
    }

    @Operation(summary = "Gets all careers")
    @GetMapping("/")
    public List<Career> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an career, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Career save( @RequestBody Career entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an career by its idCareer, requires hasAnyRole(ADMIN)")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Career update ( @RequestBody Career entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an career by its idCareer, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idCareer}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idCareer ){
        service.deleteById(idCareer);
    }

    @Operation(summary = "partial updates an career by its idCareer, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idCareer}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Career partialUpdate(@PathVariable long idCareer, @RequestBody Map<String, Object> fields){

        Career entity = findById(idCareer);

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
        return update(entity);
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Career> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCareer") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

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

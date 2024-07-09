package Proyecto.Titulacion.Magazines.Magazine;

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
@RequestMapping("/api/magazine")
@CrossOrigin({"*"})
@Tag(name = "Controller Magazine (Revista)", description = "Table magazine")
public class MagazineController {
    @Autowired
    MagazineService service;

    @Operation(summary = "Gets an magazine for your idMagazine, requires hasAnyRole")
    @GetMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Magazine findById( @PathVariable long idMagazine ){
        return service.findById(idMagazine);
    }

    @Operation(summary = "Gets all magazine, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<Magazine> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an magazine, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Magazine save( @RequestBody Magazine entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "Updates an magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Magazine update ( @RequestBody Magazine entity){
        return service.save(entity);
    }

    @Operation(summary = "Removes an magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idMagazine ){
        service.deleteById(idMagazine);
    }

    @Operation(summary = "Partial updates an magazine by its idMagazine, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idMagazine}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Magazine partialUpdate(@PathVariable long idMagazine, @RequestBody Map<String, Object> fields){

        Magazine entity = findById(idMagazine);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Magazine.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad Magazine", ex);
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
    public Page<Magazine> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idMagazine") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Magazine> findByNameMagazine(
            @RequestParam String nameMagazine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idMagazine") String sortBy) {
        return service.findByNameMagazine(nameMagazine, page, size, sortBy);
    }
}

package Proyecto.Titulacion.Magazines.Trend;

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
@RequestMapping("/api/trend")
@CrossOrigin({"*"})
@Tag(name = "Controller Trend (Tendencia)", description = "Tabla Trend")
public class TrendController {

    @Autowired
    TrendService service;

    @Operation(summary = "Gets an Trend for your idTrend")
    @GetMapping("/{idTrend}/")
    public Trend findById( @PathVariable long idTrend ){
        return service.findById(idTrend);
    }

    @Operation(summary = "Gets all Trend")
    @GetMapping("/")
    public List<Trend> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an Trend, requires hasAnyRole(ADMIN)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Trend save( @RequestBody Trend entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "Updates an Trend by its idTrend, requires hasAnyRole(ADMIN)")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Trend update ( @RequestBody Trend entity){
        return service.save(entity);
    }

    @Operation(summary = "Removes an Trend by its idTrend, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idTrend}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idTrend ){
        service.deleteById(idTrend);
    }

    @Operation(summary = "Partial updates an Trend by its idTrend, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idTrend}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Trend partialUpdate(@PathVariable long idTrend, @RequestBody Map<String, Object> fields){

        Trend entity = findById(idTrend);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Trend.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Trend", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

    @Operation(summary = "Trend Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Trend> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idTrend") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Trend")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Trend> findByTitleTrend(
            @RequestParam String titleTrend,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idTrend") String sortBy) {
        return service.findByTitleTrend(titleTrend, page, size, sortBy);
    }
}

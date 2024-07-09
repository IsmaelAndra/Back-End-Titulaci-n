package Proyecto.Titulacion.Products.ProductComment;

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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productComment")
@CrossOrigin({"*"})
@Tag(name = "Controller Product Comment (Comentario de Producto)", description = "Table Product Comment")
public class ProductCommentController {

    @Autowired
    ProductCommentService service;

    @GetMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public ProductComment findById( @PathVariable long idProductComment ){
        return service.findById(idProductComment);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<ProductComment> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment save( @RequestBody ProductComment entitiy ){
        return service.save(entitiy);
    }
    
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment update ( @RequestBody ProductComment entity){
        return service.save(entity);
    }

    @DeleteMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long idProductComment ){
        service.deleteById(idProductComment);
    }

    @PatchMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment partialUpdate(@PathVariable long idProductComment, @RequestBody Map<String, Object> fields){

        ProductComment entity = findById(idProductComment);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            try {
                Field campoEntidad = ProductComment.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad ProductComment", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }
}

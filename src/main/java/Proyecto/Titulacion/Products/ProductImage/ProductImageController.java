package Proyecto.Titulacion.Products.ProductImage;

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

@RestController
@RequestMapping("/api/productImage")
@CrossOrigin({"*"})
public class ProductImageController {
    @Autowired
    ProductImageService service;

    @GetMapping("/{idProductImage}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public ProductImage findById( @PathVariable long idProductImage ){
        return service.findById(idProductImage);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<ProductImage> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public ProductImage save( @RequestBody ProductImage entitiy ){
        return service.save(entitiy);
    }
    
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public ProductImage update ( @RequestBody ProductImage entity){
        return service.save(entity);
    }

    @DeleteMapping("/{idProductImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long idProductImage ){
        service.deleteById(idProductImage);
    }

    @PatchMapping("/{idProductImage}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public ProductImage partialUpdate(@PathVariable long idProductImage, @RequestBody Map<String, Object> fields){

        ProductImage entity = findById(idProductImage);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            try {
                Field campoEntidad = ProductImage.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad ProductImage", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }
}
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productImage")
@CrossOrigin({ "*" })
@Tag(name = "Controller Product Image (Imagenes del Producto)", description = "Table Product Image")
public class ProductImageController {
    @Autowired
    ProductImageService service;

    @Operation(summary = "Gets an Product Image for your idProductImage")
    @GetMapping("/{idProductImage}/")
    public ProductImage findById(@PathVariable long idProductImage) {
        return service.findById(idProductImage);
    }

    @Operation(summary = "Gets all Product Image")
    @GetMapping("/")
    public List<ProductImage> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an Product Image, Requiere hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ProductImage save(@RequestBody ProductImage entitiy) {
        return service.save(entitiy);
    }

    @Operation(summary = "Updates an Product Image by its idProductImage, requires hasAnyRole")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ProductImage update(@RequestBody ProductImage entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an Product Image by its idProductImage, requires hasAnyRole")
    @DeleteMapping("/{idProductImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idProductImage) {
        service.deleteById(idProductImage);
    }

    @Operation(summary = "Partially updates a Product Image by its id, Requires hasAnyRole")
    @PatchMapping("/{idProductImage}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ProductImage partialUpdate(@PathVariable long idProductImage, @RequestBody Map<String, Object> fields) {

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
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad ProductImage",
                        ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }
}
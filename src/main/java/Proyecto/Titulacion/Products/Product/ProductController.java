package Proyecto.Titulacion.Products.Product;

import java.util.Map;
import java.util.List;
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
@RequestMapping("/api/product")
@CrossOrigin({"*"})
@Tag(name = "Controller Product (Producto)", description = "Table product")
public class ProductController {

    @Autowired
    ProductService service;

    @Operation(summary = "Gets an product for your idProduct, requires hasAnyRole")
    @GetMapping("/{idProduct}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Product findById( @PathVariable long idProduct ){
        return service.findById(idProduct);
    }

    @Operation(summary = "Gets all product, requires hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<Product> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save an product, requires hasAnyRole(EMPRENDEDOR)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Product save( @RequestBody Product entitiy ){
        return service.save(entitiy);
    }
    

    @Operation(summary = "Updates an product by its idProduct, requires hasAnyRole(EMPRENDEDOR)")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Product update ( @RequestBody Product entity){
        return service.save(entity);
    }

    @Operation(summary = "Removes an product by its idProduct, requires hasAnyRole(ADMIN, EMPRENDEDOR)")
    @DeleteMapping("/{idProduct}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long idProduct ){
        service.deleteById(idProduct);
    }

    @Operation(summary = "Partial updates an product by its idProduct, requires hasAnyRole(EMPRENDEDOR)")
    @PatchMapping("/{idProduct}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Product partialUpdate(@PathVariable long idProduct, @RequestBody Map<String, Object> fields){

        Product entity = findById(idProduct);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            try {
                Field campoEntidad = Product.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad Product", ex);
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
    public Page<Product> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProduct") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Product> findByNameProduct(
            @RequestParam String nameProduct,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProduct") String sortBy) {
        return service.findByNameProduct(nameProduct, page, size, sortBy);
    }
}
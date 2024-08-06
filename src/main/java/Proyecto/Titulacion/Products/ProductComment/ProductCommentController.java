package Proyecto.Titulacion.Products.ProductComment;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import Proyecto.Titulacion.Products.Product.Product;
import Proyecto.Titulacion.Products.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productComment")
@CrossOrigin({ "*" })
@Tag(name = "Controller Product Comment (Comentarios de Producto)", description = "Table Product Comment")
public class ProductCommentController {

    @Autowired
    ProductCommentService service;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Gets an comment of Product for your idProductComment")
    @GetMapping("/{idProductComment}/")
    public ProductComment findById(@PathVariable long idProductComment) {
        return service.findById(idProductComment);
    }

    @Operation(summary = "Gets all comments of Product")
    @GetMapping("/")
    public List<ProductComment> findAll() {
        return service.findAll();
    }

    @GetMapping("/byProduct/{idProduct}/")
    public ResponseEntity<List<ProductComment>> getCommentsByIdProduct(@PathVariable Long idProduct) {
        List<ProductComment> comments = service.findByProductId(idProduct);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Save an comment Product, Requiere hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment save(@RequestBody ProductComment entity) {
        // Obtener el nombre de usuario del token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Asociar el comentario con el Producto correspondiente
        Product product = productService.findById(entity.getProduct().getIdProduct())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        entity.setProduct(product);

        // Establecer el nombre de usuario en el comentario
        entity.setUsernameCommentProduct(username);

        return service.save(entity);
    }

    @Operation(summary = "Updates an comment Product by its idProductComment, requires hasAnyRole")
    @PutMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment update(@RequestBody ProductComment entity) {
        return service.save(entity);
    }

    @Operation(summary = "Removes an comment Product by its idProductComment, requires hasAnyRole")
    @DeleteMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idProductComment) {
        service.deleteById(idProductComment);
    }

    @Operation(summary = "Partially updates a comment by its id, Requires hasAnyRole")
    @PatchMapping("/{idProductComment}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public ProductComment partialUpdate(@PathVariable long idProductComment, @RequestBody Map<String, Object> fields) {

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
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad ProductComment", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }
}

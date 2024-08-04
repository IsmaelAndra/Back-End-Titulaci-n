package Proyecto.Titulacion.Products.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Proyecto.Titulacion.Products.ProductImage.ProductImage;
import Proyecto.Titulacion.Products.ProductImage.ProductImageService;
import Proyecto.Titulacion.User.User.User;
import Proyecto.Titulacion.User.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;

@RestController
@RequestMapping("/api/product")
@CrossOrigin({ "*" })
@Tag(name = "Controller Product (Producto)", description = "Table product")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    private final String UPLOAD_DIR = "uploads/";

    @Operation(summary = "Gets a product for your idProduct")
    @GetMapping("/{idProduct}/")
    public ResponseEntity<Product> getProductById(@PathVariable Long idProduct) {
        Optional<Product> product = service.findById(idProduct);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all products")
    @GetMapping("/")
    public List<Product> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save a product with images, requires hasAnyRole(EMPRENDEDOR)")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Product> saveProduct(
            @RequestParam("nameProduct") String nameProduct,
            @RequestParam("descriptionProduct") String descriptionProduct,
            @RequestParam("priceProduct") Double priceProduct,
            @RequestParam("images") List<MultipartFile> images,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        Product product = new Product();
        product.setNameProduct(nameProduct);
        product.setDescriptionProduct(descriptionProduct);
        product.setPriceProduct(priceProduct);
        product.setUser(user);

        Product savedProduct = service.save(product);

        if (!images.isEmpty()) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String imageUrl = saveImage(image);
                    ProductImage productImage = new ProductImage();
                    productImage.setUrlProductImage(imageUrl);
                    productImage.setProduct(savedProduct);
                    productImageService.save(productImage);
                }
            }
        }

        return ResponseEntity.ok(savedProduct);
    }

    @Operation(summary = "Updates a product by its idProduct, requires hasAnyRole(EMPRENDEDOR)")
    @PutMapping("/{idProduct}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Product> updateProduct(@PathVariable long idProduct, @RequestBody Product product) {
        Optional<Product> optionalProduct = service.findById(idProduct);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setNameProduct(product.getNameProduct());
            existingProduct.setDescriptionProduct(product.getDescriptionProduct());
            existingProduct.setPriceProduct(product.getPriceProduct());
            existingProduct.setUser(product.getUser());
            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String saveImage(MultipartFile image) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path copyLocation = Paths.get(uploadPath + File.separator + fileName);
            Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return copyLocation.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo de imagen");
        }
    }

    @Operation(summary = "Removes a product by its idProduct, requires hasAnyRole(ADMIN, EMPRENDEDOR)")
    @DeleteMapping("/{idProduct}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idProduct) {
        service.deleteById(idProduct);
    }

    @Operation(summary = "Partial updates a product by its idProduct, requires hasAnyRole(EMPRENDEDOR)")
    @PatchMapping("/{idProduct}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Product> partialUpdate(@PathVariable long idProduct,
            @RequestBody Map<String, Object> fields) {
        Optional<Product> optionalProduct = service.findById(idProduct);

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product entity = optionalProduct.get();

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

        Product updatedProduct = service.save(entity);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Product Pagination")
    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Product> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProduct") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for Product")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Product> findByNameProduct(
            @RequestParam String nameProduct,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProduct") String sortBy) {
        return service.findByNameProduct(nameProduct, page, size, sortBy);
    }

    @Operation(summary = "Daily, monthly and yearly Product statistics.")
    @GetMapping("/stats")
    public Map<String, Long> getProductStats(@RequestParam String period) {
        return service.getProductStats(period);
    }
}

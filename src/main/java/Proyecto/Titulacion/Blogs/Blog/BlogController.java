package Proyecto.Titulacion.Blogs.Blog;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import Proyecto.Titulacion.User.User.User;
import Proyecto.Titulacion.User.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin({ "*" })
@Tag(name = "Controller Blog", description = "Table Blogs")
public class BlogController {

    @Autowired
    UserService userService;

    @Autowired
    BlogService service;

    private static final String UPLOAD_DIR = "uploads/";

    @Operation(summary = "Get a blog by its idBlog")
    @GetMapping("/{idBlog}/")
    public ResponseEntity<Blog> getBlogEntity(@PathVariable Long idBlog) {
        Optional<Blog> blog = service.findById(idBlog);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all blogs")
    @GetMapping("/")
    public List<Blog> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Save a blog, Requiere hasAnyRole")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Blog> saveBlog(
            @RequestParam("titleBlog") String titleBlog,
            @RequestParam("subtitleBlog") String subtitleBlog,
            @RequestParam("contentBlog") String contentBlog,
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        Blog blog = new Blog();
        blog.setTitleBlog(titleBlog);
        blog.setSubtitleBlog(subtitleBlog);
        blog.setContentBlog(contentBlog);
        blog.setUser(user);

        if (!image.isEmpty()) {
            String imageUrl = saveImage(image);
            blog.setUrl_contentBlog(imageUrl);
        }

        Blog saveBlog = service.save(blog);

        return ResponseEntity.ok(saveBlog);
    }

    @Operation(summary = "Updates an blog by its idBlog, Requiere hasAnyRole")
    @PutMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable long idBlog,
            @RequestParam("titleBlog") String titleBlog,
            @RequestParam("subtitleBlog") String subtitleBlog,
            @RequestParam("contentBlog") String contentBlog,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Optional<Blog> optionalBlog = service.findById(idBlog);
        if (optionalBlog.isPresent()) {
            Blog existingBlog = optionalBlog.get();
            existingBlog.setTitleBlog(titleBlog);
            existingBlog.setSubtitleBlog(subtitleBlog);
            existingBlog.setContentBlog(contentBlog);
            service.save(existingBlog);

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                existingBlog.setUrl_contentBlog(imageUrl);
            }

            Blog updateBlog = service.save(existingBlog);
            return ResponseEntity.ok(updateBlog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Removes a blog by its idBlog, Requiere hasAnyRole")
    @DeleteMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idBlog) {
        service.deleteById(idBlog);
    }

    @Operation(summary = "Partial update an blog by its idBlog, Requiere hasAnyRole")
    @PatchMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public ResponseEntity<Blog> partialUpdate(@PathVariable long idBlog, @RequestBody Map<String, Object> fields) {
        Optional<Blog> optionalBlog = service.findById(idBlog);

        if (!optionalBlog.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Blog entity = optionalBlog.get();

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Blog.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Blog", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }

        Blog updateBlog = service.save(entity);
        return ResponseEntity.ok(updateBlog);
    }

    @Operation(summary = "Blog Pagination")
    @GetMapping("/paginated")
    public Page<Blog> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "idBlog") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @Operation(summary = "Search for blog")
    @GetMapping("/search")
    public Page<Blog> findByTitleBlog(
            @RequestParam String titleBlog,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "idBlog") String sortBy) {
        return service.findByTitleBlog(titleBlog, page, size, sortBy);
    }

    @Operation(summary = "Daily, monthly and yearly blog statistics.")
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String, Long> getBlogStats(@RequestParam String period) {
        return service.getBlogStats(period);
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
            return "uploads/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo de imagen");
        }
    }
}

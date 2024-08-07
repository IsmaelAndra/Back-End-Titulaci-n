package Proyecto.Titulacion.Blogs.CommentBlog;

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

import Proyecto.Titulacion.Blogs.Blog.Blog;
import Proyecto.Titulacion.Blogs.Blog.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/blogComments")
@CrossOrigin({ "*" })
@Tag(name = "Controlador Comments (Comentarios)", description = "Tabla Comentarios")
public class CommentBlogController {

    @Autowired
    CommentBlogService service;

    @Autowired
    private BlogService blogService;

    @Operation(summary = "Gets an comment of blog for your idCommentBlog")
    @GetMapping("/{idCommentBlog}/")
    public CommentBlog findById(@PathVariable long idCommentBlog) {
        return service.findById(idCommentBlog);
    }

    @Operation(summary = "Gets all comments of blog")
    @GetMapping("/")
    public List<CommentBlog> findAll() {
        return service.findAll();
    }

    @GetMapping("/byBlog/{idBlog}/")
    public ResponseEntity<List<CommentBlog>> getCommentsByIdBlog(@PathVariable Long idBlog) {
        List<CommentBlog> comments = service.findByBlogId(idBlog);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Save a comment blog, requires hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog save(@RequestBody CommentBlog entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Blog blog = blogService.findById(entity.getBlog().getIdBlog())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        entity.setBlog(blog);
        entity.setUsernameCommentBlog(username);

        return service.save(entity);
    }

    @Operation(summary = "Updates an achievement by its idCommentBlog, requires hasAnyRole")
    @PutMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog update(@PathVariable long idCommentBlog, @RequestBody CommentBlog entity) {
        CommentBlog existingComment = service.findById(idCommentBlog);
        existingComment.setContentCommentBlog(entity.getContentCommentBlog());
        return service.save(existingComment);
    }

    @Operation(summary = "Removes an comment blog by its idCommentBlog, requires hasAnyRole")
    @DeleteMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public void deleteById(@PathVariable long idCommentBlog) {
        service.deleteById(idCommentBlog);
    }

    @Operation(summary = "Partially updates a comment by its id, Requires hasAnyRole")
    @PatchMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog partialUpdate(@PathVariable long idCommentBlog, @RequestBody Map<String, Object> fields) {
        CommentBlog entity = findById(idCommentBlog);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = CommentBlog.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' no encontrado en la entidad CommentBlog",
                        ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(idCommentBlog, entity);
    }
}

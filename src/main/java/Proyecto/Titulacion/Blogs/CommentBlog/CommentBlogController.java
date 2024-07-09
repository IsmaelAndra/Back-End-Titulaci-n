package Proyecto.Titulacion.Blogs.CommentBlog;

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
@RequestMapping("/api/entidad")
@CrossOrigin({"*"})
@Tag(name = "Controlador Authority (Permisos)", description = "Tabla authorities")
public class CommentBlogController {

    @Autowired
    CommentBlogService service;

    @Operation(summary = "gets an comment of blog for your idCommentBlog, Requiere hasAnyRole")
    @GetMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public CommentBlog findById( @PathVariable long idCommentBlog ){
        return service.findById(idCommentBlog);
    }

    @Operation(summary = "Gets all comments of blog, Requiere hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<CommentBlog> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an comment blog, Requiere hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog save( @RequestBody CommentBlog entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an achievement by its idCommentBlog, requires hasAnyRole")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog update ( @RequestBody CommentBlog entity){
        return service.save(entity);
    }

    @Operation(summary = "removes an comment blog by its idCommentBlog, requires hasAnyRole")
    @DeleteMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long idCommentBlog ){
        service.deleteById(idCommentBlog);
    }

    @Operation(summary = "partial updates an cooment blog by its idCommentBlog, requires hasAnyRole")
    @PatchMapping("/{idCommentBlog}/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public CommentBlog partialUpdate(@PathVariable long idCommentBlog, @RequestBody Map<String, Object> fields){

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
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad CommentBlog", ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("No se puede acceder o establecer el campo '" + fieldName + "'", ex);
            } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar el campo '" + fieldName + "'", ex);
            }
        }
        return update(entity);
    }

}

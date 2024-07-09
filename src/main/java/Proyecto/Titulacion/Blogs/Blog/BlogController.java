package Proyecto.Titulacion.Blogs.Blog;

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
@RequestMapping("/api/blog")
@CrossOrigin({"*"})
@Tag(name = "Controller blog", description = "Table blogs")
public class BlogController {

    @Autowired
    BlogService service;

    @Operation(summary = "get a blog by its idBlog, Requiere hasAnyRole")
    @GetMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Blog findById( @PathVariable long idBlog ){
        return service.findById(idBlog);
    }

    @Operation(summary = "gets all blogs, Requiere hasAnyRole")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public List<Blog> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save a blog, Requiere hasAnyRole")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Blog save( @RequestBody Blog entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an blog by its idBlog, Requiere hasAnyRole")
    @PutMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Blog update ( @RequestBody Blog entity){
        return service.save(entity);
    }

    @Operation(summary = "removes a blog by its idBlog, Requiere hasAnyRole")
    @DeleteMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long idBlog ){
        service.deleteById(idBlog);
    }

    @Operation(summary = "partial update an blog by its idBlog, Requiere hasAnyRole")
    @PatchMapping("/{idBlog}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Blog partialUpdate(@PathVariable long idBlog, @RequestBody Map<String, Object> fields){

        Blog entity = findById(idBlog);

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
        return update(entity);
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Blog> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idBlog") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Blog> findByTitleBlog(
            @RequestParam String titleBlog,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idBlog") String sortBy) {
        return service.findByTitleBlog(titleBlog, page, size, sortBy);
    }
}

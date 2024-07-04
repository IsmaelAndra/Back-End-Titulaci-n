package Proyecto.Titulacion.Blogs.Blog;

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
@RequestMapping("/api/blog")
@CrossOrigin({"*"})
@Tag(name = "Controller blog", description = "Table blogs")
public class BlogController {

    @Autowired
    BlogService service;

    @Operation(summary = "get a blog by its id, Requiere hasAnyRole")
    @GetMapping("/{id}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Blog findById( @PathVariable long id ){
        return service.findById(id);
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
    
    @Operation(summary = "updates an blog by its id, Requiere hasAnyRole")
    @PutMapping("/{id}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Blog update ( @RequestBody Blog entity){
        return service.save(entity);
    }

    @Operation(summary = "removes a blog by its id, Requiere hasAnyRole")
    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN','EMPRENDEDOR')")
    public void deleteById( @PathVariable long id ){
        service.deleteById(id);
    }

    @Operation(summary = "partial update an blog by its id, Requiere hasAnyRole")
    @PatchMapping("/{id}/")
    @PreAuthorize("hasAnyRole('EMPRENDEDOR')")
    public Blog partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields){

        Blog entity = findById(id);

        // itera sobre los campos que se desean actualizar
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            // utiliza reflection para establecer el valor del campo en la entidad
            try {
                Field campoEntidad = Blog.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // maneja la excepción si ocurre algún error al acceder al campo
            }
        }
        return update(entity);
    }

}

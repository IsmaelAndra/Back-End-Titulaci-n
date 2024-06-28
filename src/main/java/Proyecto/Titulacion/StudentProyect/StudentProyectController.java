package Proyecto.Titulacion.StudentProyect;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;


import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/studentProyect")
@CrossOrigin({"*"})
public class StudentProyectController {

    @Autowired
    StudentProyectService service;

    @GetMapping("/{id}/")
    public StudentProyect findById( @PathVariable long id ){
        return service.findById(id);
    }

    @GetMapping("/")
    public List<StudentProyect> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public StudentProyect save( @RequestBody StudentProyect entitiy ){
        return service.save(entitiy);
    }
    
    @PutMapping("/")
    public StudentProyect update ( @RequestBody StudentProyect entity){
        return service.save(entity);
    }

    @DeleteMapping("/{id}/")
    public void deleteById( @PathVariable long id ){
        service.deleteById(id);
    }

      @PatchMapping("/{id}/")
    public StudentProyect partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> fields){

        StudentProyect entity = findById(id);

        // itera sobre los campos que se desean actualizar
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();
            
            // utiliza reflection para establecer el valor del campo en la entidad
            try {
                Field campoEntidad = StudentProyect.class.getDeclaredField(fieldName);
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

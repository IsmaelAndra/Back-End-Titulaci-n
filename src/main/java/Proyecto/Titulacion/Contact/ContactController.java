package Proyecto.Titulacion.Contact;

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
@RequestMapping("/api/contact")
@CrossOrigin({"*"})
@Tag(name = "Controller Contact (contacto)", description = "Tabla contact")
public class ContactController {

    @Autowired
    ContactService service;

    @Operation(summary = "gets an contact for your idContact, requires hasAnyRole")
    @GetMapping("/{idContact}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Contact findById( @PathVariable long idContact ){
        return service.findById(idContact);
    }

    @Operation(summary = "Gets all contacts, requires hasAnyRole(ADMIN)")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Contact> findAll() {
        return service.findAll();
    }

    @Operation(summary = "save an contact, requires hasAnyRole(USER, EMPRENDEDOR)")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER','EMPRENDEDOR')")
    public Contact save( @RequestBody Contact entitiy ){
        return service.save(entitiy);
    }
    
    @Operation(summary = "updates an achievement by its idContact, requires hasAnyRole(ADMIN)")
    @PutMapping("/{idContact}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Contact update ( @RequestBody Contact entity){
        return service.save(entity);
    }
    @Operation(summary = "removes an contact by its idContact, requires hasAnyRole(ADMIN)")
    @DeleteMapping("/{idContact}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById( @PathVariable long idContact ){
        service.deleteById(idContact);
    }

    @Operation(summary = "partial updates an contacts by its idContact, requires hasAnyRole(ADMIN)")
    @PatchMapping("/{idContact}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Contact partialUpdate(@PathVariable long idContact, @RequestBody Map<String, Object> fields){

        Contact entity = findById(idContact);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object fieldValue = field.getValue();

            try {
                Field campoEntidad = Contact.class.getDeclaredField(fieldName);
                campoEntidad.setAccessible(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                campoEntidad.set(entity, mapper.convertValue(fieldValue, campoEntidad.getType()));
            } catch (NoSuchFieldException ex) {
                throw new IllegalArgumentException(
                        "Campo '" + fieldName + "' no encontrado en la entidad Contact", ex);
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
    public Page<Contact> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idContact") String sortBy) {
        return service.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    public Page<Contact> findByNameContact(
            @RequestParam String nameContact,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idContact") String sortBy) {
        return service.findByNameContact(nameContact, page, size, sortBy);
    }
}

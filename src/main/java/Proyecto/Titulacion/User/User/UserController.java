package Proyecto.Titulacion.User.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user/")
@Tag(name = "Controller Users (Usuarios)", description = "Tabla user")
public class UserController {
    @Autowired 
    private UserService userService;

    @PostMapping("/")
    @Operation(summary = "Crear un Usuario")
    public User save(@RequestBody User entity)
    {
        return userService.save(entity);
    }

    @GetMapping("/{id_user}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    @Operation(summary = "Obtener un Usuario")
    public User findUser(@PathVariable long id_user)
    {
        return userService.findById(id_user);
    }
    
    @PutMapping("/{id_user}/")
    @Operation(summary = "Actualizar un Usuario")
    public User update(@RequestBody User entity)
    {
        return userService.save(entity);
    }

    @DeleteMapping("/{id_user}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Eliminar un Usuario")
    public void deleteByID(@PathVariable long id_user)
    {
        userService.deleteByID(id_user);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Obtener todos los Usuarios")
    public List<User> findAll()
    {
        return userService.findAll();
    }
}

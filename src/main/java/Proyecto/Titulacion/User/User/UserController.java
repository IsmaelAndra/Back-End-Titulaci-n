package Proyecto.Titulacion.User.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public User save(@RequestBody User entity) {
        return userService.save(entity);
    }

    @GetMapping("/{idUser}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    @Operation(summary = "Obtener un Usuario")
    public User findUser(@PathVariable long idUser) {
        return userService.findById(idUser);
    }

    @PutMapping("/{idUser}/")
    @Operation(summary = "Actualizar un Usuario")
    public User update(@RequestBody User entity) {
        return userService.save(entity);
    }

    @DeleteMapping("/{idUser}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Eliminar un Usuario")
    public void deleteByID(@PathVariable long idUser) {
        userService.deleteByID(idUser);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Obtener todos los Usuarios")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Obtener usuarios paginados")
    public Page<User> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idUser") String sortBy) {
        return userService.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Buscar usuarios por nombre")
    public Page<User> findByNameUser(
            @RequestParam String nameUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idUser") String sortBy) {
        return userService.findByNameUser(nameUser, page, size, sortBy);
    }
}

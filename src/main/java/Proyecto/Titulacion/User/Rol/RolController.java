package Proyecto.Titulacion.User.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/rol")
@CrossOrigin({ "*" })
@Tag(name = "Controller Rol (Roles)", description = "Tabla rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Crear un Rol")
    public Rol save(@RequestBody Rol entity) {
        return rolService.save(entity);
    }

    @GetMapping("/{id_rol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Obtener un Rol")
    public Rol findRol(@PathVariable long id_rol) {
        return rolService.findById(id_rol);
    }

    @PutMapping("/{id_rol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Actualizar un Rol")
    public Rol update(@RequestBody Rol entity) {
        return rolService.save(entity);
    }

    @DeleteMapping("/{id_rol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Eliminar un Rol")
    public void deleteByID(@PathVariable long id_rol) {
        rolService.deleteByID(id_rol);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Obtener todos los Roles")
    public List<Rol> findAll() {
        return rolService.findAll();
    }
}

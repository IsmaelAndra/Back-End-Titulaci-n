package Proyecto.Titulacion.User.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/rol")
@CrossOrigin({ "*" })
@Tag(name = "Controller Rol (Roles)", description = "Tabla rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Create a Role")
    public Rol save(@RequestBody Rol entity) {
        return rolService.save(entity);
    }

    @GetMapping("/{idRol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Get a Role")
    public Rol findRol(@PathVariable long idRol) {
        return rolService.findById(idRol);
    }

    @PutMapping("/{idRol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update a Role")
    public Rol update(@RequestBody Rol entity) {
        return rolService.save(entity);
    }

    @DeleteMapping("/{idRol}/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Delete a Role")
    public void deleteByID(@PathVariable long idRol) {
        rolService.deleteByID(idRol);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Get all Roles")
    public List<Rol> findAll() {
        return rolService.findAll();
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Get paginated roles")
    public Page<Rol> findPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idRol") String sortBy) {
        return rolService.findPaginated(page, size, sortBy);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Search roles by name")
    public Page<Rol> findByNameRol(
            @RequestParam String nameRol,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idRol") String sortBy) {
        return rolService.findByNameRol(nameRol, page, size, sortBy);
    }
}

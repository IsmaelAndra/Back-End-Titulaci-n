package Proyecto.Titulacion.User.User;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Proyecto.Titulacion.Careers.Career.Career;
import Proyecto.Titulacion.User.Rol.Rol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/user/")
@Tag(name = "Controller Users (Usuarios)", description = "Tabla user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crear un Usuario")
    public ResponseEntity<User> saveUser(
        @RequestParam("nameUser") String nameUser,
        @RequestParam("lastnameUser") String lastnameUser,
        @RequestParam("username") String username,
        @RequestParam("phoneUser") Integer phoneUser,
        @RequestParam("emailUser") String emailUser,
        @RequestParam("password") String password,
        @RequestParam("passVerificationUser") String passVerificationUser,
        @RequestParam("verified") boolean verified,
        @RequestParam("verificationCode") String verificationCode,
        @RequestParam("rol") Rol rol,
        @RequestParam("career") Career career,
        @RequestParam("photoUser") MultipartFile photoUser) {

        String photoUrl = imageService.saveImage(photoUser);
        
        User user = new User();
        user.setNameUser(nameUser);
        user.setLastnameUser(lastnameUser);
        user.setUsername(username);
        user.setPhoneUser(phoneUser);
        user.setEmailUser(emailUser);
        user.setPassword(password);
        user.setPassVerificationUser(passVerificationUser);
        user.setVerified(verified);
        user.setVerificationCode(verificationCode);
        user.setRol(rol);
        user.setCareer(career);
        user.setPhotoUser(photoUrl);
        
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping(value = "/{idUser}/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar un Usuario")
    public ResponseEntity<User> updateUser(
        @PathVariable long idUser,
        @RequestParam("nameUser") String nameUser,
        @RequestParam("lastnameUser") String lastnameUser,
        @RequestParam("username") String username,
        @RequestParam("phoneUser") Integer phoneUser,
        @RequestParam("emailUser") String emailUser,
        @RequestParam("password") String password,
        @RequestParam("passVerificationUser") String passVerificationUser,
        @RequestParam("verified") boolean verified,
        @RequestParam("verificationCode") String verificationCode,
        @RequestParam("rol") Rol rol,
        @RequestParam("career") Career career,
        @RequestParam(value = "photoUser", required = false) MultipartFile photoUser) {

        User user = userService.findById(idUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        user.setNameUser(nameUser);
        user.setLastnameUser(lastnameUser);
        user.setUsername(username);
        user.setPhoneUser(phoneUser);
        user.setEmailUser(emailUser);
        user.setPassword(password);
        user.setPassVerificationUser(passVerificationUser);
        user.setVerified(verified);
        user.setVerificationCode(verificationCode);
        user.setRol(rol);
        user.setCareer(career);
        
        if (photoUser != null && !photoUser.isEmpty()) {
            String photoUrl = imageService.saveImage(photoUser);
            user.setPhotoUser(photoUrl);
        }
        
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{idUser}/")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EMPRENDEDOR')")
    @Operation(summary = "Obtener un Usuario")
    public User findUser(@PathVariable long idUser) {
        return userService.findById(idUser);
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

    @GetMapping("/stats")
    public Map<String, Long> getUserStats(@RequestParam String period) {
        return userService.getUserStats(period);
    }
}

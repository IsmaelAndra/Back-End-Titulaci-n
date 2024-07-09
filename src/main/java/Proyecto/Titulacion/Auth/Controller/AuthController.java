package Proyecto.Titulacion.Auth.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Proyecto.Titulacion.Auth.Request.LoginRequest;
import Proyecto.Titulacion.Auth.Request.RegisterRequest;
import Proyecto.Titulacion.Auth.Response.AuthResponse;
import Proyecto.Titulacion.Auth.Services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin({"*"})
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.login(request, response));
    }

    @PostMapping(value="register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.register(request, response));
    }

    @PostMapping(value="verify")
    public ResponseEntity<String> verify(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String verificationCode = request.get("codigoVerificacion");
        try {
            boolean verified = authService.verifyCode(username, verificationCode);
            if (verified) {
                return ResponseEntity.ok("Verificación exitosa");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de verificación invalido");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

package Proyecto.Titulacion.Auth.Controller;

import java.util.HashMap;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin({ "*" })
@Tag(name = "Controller Authentication (Autenticación)", description = "Table Authentication")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @Operation(summary = "Login in users")
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(request, response));
    }

    @Operation(summary = "Register new users")
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.register(request, response));
    }

    @Operation(summary = "Verify Email")
    @PostMapping(value = "verify")
    public ResponseEntity<Map<String, String>> verify(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String verificationCode = request.get("codigoVerificacion");
        Map<String, String> response = new HashMap<>();
        try {
            boolean verified = authService.verifyCode(username, verificationCode);
            if (verified) {
                response.put("message", "Verificación exitosa");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Código de verificación inválido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

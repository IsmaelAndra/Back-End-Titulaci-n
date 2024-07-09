package Proyecto.Titulacion.Auth.Services;

import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Proyecto.Titulacion.Auth.Request.LoginRequest;
import Proyecto.Titulacion.Auth.Request.RegisterRequest;
import Proyecto.Titulacion.Auth.Response.AuthResponse;
import Proyecto.Titulacion.User.Rol.Rol;
import Proyecto.Titulacion.User.Rol.RolRepository;
import Proyecto.Titulacion.User.User.User;
import Proyecto.Titulacion.User.User.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.getToken(user);

            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // solo en HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 día
            response.addCookie(jwtCookie);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }

    public AuthResponse register(RegisterRequest request, HttpServletResponse response) {
        String roleName = request.getRole();
        Rol userRole = rolRepository.findByNameRol(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String encodedPassword_Veri = passwordEncoder.encode(request.getPassVerificationUser());
        String verificationCode = generateVerificationCode();

        User user = User.builder()
                .photoUser(request.getPhotoUser())
                .nameUser(request.getNameUser())
                .username(request.getUsername())
                .lastnameUser(request.getLastnameUser())
                .phoneUser(request.getPhoneUser())
                .emailUser(request.getEmailUser())
                .password(encodedPassword)
                .passVerificationUser(encodedPassword_Veri)
                .verified(false)
                .verificationCode(verificationCode)
                .rol(userRole)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
    
    public boolean verifyCode(String username, String verificationCode) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getVerificationCode().equals(verificationCode)) {
                user.setVerified(true);
                user.setVerificationCode(null);
                userRepository.save(user);
                return true;
            } else {
                return false; // Código de verificación incorrecto
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}

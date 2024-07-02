package Proyecto.Titulacion.Auth.Services;

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
        Rol userRole = rolRepository.findByNamerol(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String encodedPassword_Veri = passwordEncoder.encode(request.getPass_verification_user());

        User user = User.builder()
                .photo_user(request.getPhoto_user())
                .name_user(request.getName_user())
                .username(request.getUsername())
                .lastname_user(request.getLastname_user())
                .dateofbirth_user(request.getDateofbirth_user())
                .gender_user(request.getGender_user())
                .address_user(request.getAddress_user())
                .phone_user(request.getPhone_user())
                .email_user(request.getEmail_user())
                .password(encodedPassword)
                .pass_verification_user(encodedPassword_Veri)
                .rol(userRole)
                .build();

        userRepository.save(user);

        String token = jwtService.getToken(user);

        // Agregar cookie con el token
        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Asegúrate de usar HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); // Expira en 1 día
        response.addCookie(jwtCookie);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}

package Proyecto.Titulacion.Auth.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String photo_user;
    private String name_user;
    private String username;
    private String lastname_user;
    private Integer phone_user;
    private String email_user;
    private String password;
    private String pass_verification_user;
    private String role;
}

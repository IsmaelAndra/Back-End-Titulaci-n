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
    private String photoUser;
    private String nameUser;
    private String username;
    private String lastnameUser;
    private Integer phoneUser;
    private String emailUser;
    private String password;
    private String passVerificationUser;
    private boolean verified;
    private String verificationCode;
    private String role;
}

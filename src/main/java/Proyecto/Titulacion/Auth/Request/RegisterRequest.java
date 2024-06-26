package Proyecto.Titulacion.Auth.Request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String photo_user;
    String name_user;
    String username;
    String lastname_user;
    LocalDate dateofbirth_user;
    String gender_user;
    String address_user;
    Integer phone_user;
    String email_user;
    String password;
    String pass_verification_user;
}

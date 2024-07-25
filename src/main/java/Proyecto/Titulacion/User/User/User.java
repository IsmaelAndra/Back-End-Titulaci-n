package Proyecto.Titulacion.User.User;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.Careers.Career.Career;
import Proyecto.Titulacion.User.Rol.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends Audit implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long idUser;

    @Column(length = 500)
    private String photoUser;

    @Column(length = 30)
    private String nameUser;

    @Column(length = 30, unique = true)
    private String username; // NO TOCAR //

    @Column(length = 30)
    private String lastnameUser;

    @Column(length = 9, unique = true)
    private Integer phoneUser;

    @Column(length = 100, unique = true)
    private String emailUser;

    @Column
    private String password;

    @Column
    private String passVerificationUser;

    @Column
    private boolean verified;

    @Column(length = 6)
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idCareer", referencedColumnName = "idCareer")
    private Career career;

    public long getIdUser() {
        return idUser;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getUsername() {
        return username;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public Integer getPhoneUser() {
        return phoneUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getPassword() {
        return password;
    }

    public String getPassVerificationUser() {
        return passVerificationUser;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public Rol getRol() {
        return rol;
    }

    public Career getCareer() {
        return career;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastnameUser(String lastnameUser) {
        this.lastnameUser = lastnameUser;
    }

    public void setPhoneUser(Integer phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassVerificationUser(String passVerificationUser) {
        this.passVerificationUser = passVerificationUser;
    }

    public void setVerified() {
        this.verified = false;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol != null) {
            return List.of(new SimpleGrantedAuthority(rol.getNameRol()));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_DEFAULT"));
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package Proyecto.Titulacion.User.Rol;

import Proyecto.Titulacion.Audit.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="rol")
public class Rol extends Audit {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String EMPRENDEDOR = "EMPRENDEDOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long idRol;
    
    @Column(length = 30)
    private String nameRol;
    
    @Column(length = 100)
    private String descriptionRol;

    public long getIdRol() {
        return idRol;
    }

    public String getNameRol() {
        return nameRol;
    }

    public String getDescriptionRol() {
        return descriptionRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    public void setDescriptionRol(String descriptionRol) {
        this.descriptionRol = descriptionRol;
    }
}

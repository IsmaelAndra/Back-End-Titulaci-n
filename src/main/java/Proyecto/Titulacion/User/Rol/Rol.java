package Proyecto.Titulacion.User.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rol")
public class Rol {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_rol;
    @Column(length = 30)
    private String namerol;
    @Column(length = 100)
    private String description_rol;

    // @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    // private List<User> user;
}

package Proyecto.Titulacion.Careers.Career;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
        
@Data
@Entity
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_career;
    @Column(length = 100)
    private String name_career;
    @Column(length = 200)
    private String description_career;
            
}

package Proyecto.Titulacion.Magazine;

import Proyecto.Titulacion.Career.Career;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
        
@Data
@Entity
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_magazine;
    @Column(length = 100)
    private String name_megazine;
    @Column(length = 300)
    private String description_megazine;

    @ManyToOne
    @JoinColumn(name = "id_career", referencedColumnName="id_career")
    private Career career;
            
}

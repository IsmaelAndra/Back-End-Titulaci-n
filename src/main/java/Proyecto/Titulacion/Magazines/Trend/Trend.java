package Proyecto.Titulacion.Magazines.Trend;

import Proyecto.Titulacion.Magazines.Magazine.Magazine;
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
public class Trend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_trend;
    @Column(length = 100)
    private String title_trend;
    @Column(length = 100)
    private String description_trend;

    @ManyToOne
    @JoinColumn(name = "id_magazine", referencedColumnName="id_magazine")
    private Magazine magazine;
            
}
package Proyecto.Titulacion.Magazines.Show;

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
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_show;
    @Column(length = 100)
    private String title_show;
    @Column(length = 300)
    private String description_show;
    @Column(length = 500)
    private String url_image_show;

    @ManyToOne
    @JoinColumn(name = "id_magazine", referencedColumnName="id_magazine")
    private Magazine magazine;
            
}

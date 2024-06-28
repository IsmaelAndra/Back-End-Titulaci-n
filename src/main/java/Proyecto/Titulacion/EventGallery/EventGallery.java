package Proyecto.Titulacion.EventGallery;

import Proyecto.Titulacion.Event.Event;
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
public class EventGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_event_gallery;
    @Column(length = 50)
    private String title_event_gallery;
    @Column(length = 100)
    private String description_event_gallery;
    @Column(length = 500)
    private String url_image_event_gallery;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName="id_event")
    private Event event;
            
}

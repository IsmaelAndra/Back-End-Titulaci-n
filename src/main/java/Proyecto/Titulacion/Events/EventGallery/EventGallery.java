package Proyecto.Titulacion.Events.EventGallery;

import Proyecto.Titulacion.Events.Event.Event;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEventGallery;

    @Column(length = 50)
    private String titleEventGallery;

    @Column(length = 100)
    private String descriptionEventGallery;

    @Column(length = 500)
    private String urlImageEventGallery;

    @ManyToOne
    @JoinColumn(name = "idEvent", referencedColumnName = "idEvent")
    private Event event;

    public long getIdEventGallery() {
        return idEventGallery;
    }

    public String getTitleEventGallery() {
        return titleEventGallery;
    }

    public String getDescriptionEventGallery() {
        return descriptionEventGallery;
    }

    public String getUrlImageEventGallery() {
        return urlImageEventGallery;
    }

    public Event getEvent() {
        return event;
    }

    public void setIdEventGallery(long idEventGallery) {
        this.idEventGallery = idEventGallery;
    }

    public void setTitleEventGallery(String titleEventGallery) {
        this.titleEventGallery = titleEventGallery;
    }

    public void setDescriptionEventGallery(String descriptionEventGallery) {
        this.descriptionEventGallery = descriptionEventGallery;
    }

    public void setUrlImageEventGallery(String urlImageEventGallery) {
        this.urlImageEventGallery = urlImageEventGallery;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

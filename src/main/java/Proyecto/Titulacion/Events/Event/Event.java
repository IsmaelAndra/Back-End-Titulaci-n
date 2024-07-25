package Proyecto.Titulacion.Events.Event;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.User.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Event extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEvent;
    @Column(length = 100)
    private String titleEvent;
    @Column(length = 500)
    private String descriptionEvent;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dateEvent;
    @Column
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime hourEvent;
    @Column(length = 100)
    private String placeEvent;
    @Column(length = 500)
    private String url_imageEvent;
    
    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName="idUser")
    private User user;

    public long getIdEvent() {
        return idEvent;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public LocalTime getHourEvent() {
        return hourEvent;
    }

    public String getPlaceEvent() {
        return placeEvent;
    }

    public String getUrl_imageEvent() {
        return url_imageEvent;
    }

    public User getUser() {
        return user;
    }

    public void setIdEvent(long idEvent) {
        this.idEvent = idEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public void setDateEvent(LocalDate dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setHourEvent(LocalTime hourEvent) {
        this.hourEvent = hourEvent;
    }

    public void setPlaceEvent(String placeEvent) {
        this.placeEvent = placeEvent;
    }

    public void setUrl_imageEvent(String url_imageEvent) {
        this.url_imageEvent = url_imageEvent;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

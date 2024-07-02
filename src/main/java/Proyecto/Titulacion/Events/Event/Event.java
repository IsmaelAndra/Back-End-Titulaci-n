package Proyecto.Titulacion.Events.Event;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import Proyecto.Titulacion.User.User.User;
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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_event;
    @Column(length = 100)
    private String title_event;
    @Column(length = 500)
    private String description_event;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date_event;
    @Column
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime hour_event;
    @Column(length = 100)
    private String place_event;
    @Column(length = 500)
    private String url_image_event;
    
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName="id_user")
    private User user;
}

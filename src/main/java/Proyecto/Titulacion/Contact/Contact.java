package Proyecto.Titulacion.Contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
        
@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_Contact;
    @Column(length = 100)
    private String name_contact;
    @Column(length = 100)
    private String email_contact;
    @Column(length = 500)
    private String message_contact;
            
}
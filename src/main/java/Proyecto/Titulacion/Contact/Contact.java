package Proyecto.Titulacion.Contact;

import Proyecto.Titulacion.Audit.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Contact extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idContact;

    @Column(length = 100)
    private String nameContact;

    @Column(length = 100)
    private String emailContact;

    @Column(length = 500)
    private String messageContact;

    public long getIdContact() {
        return idContact;
    }

    public String getNameContact() {
        return nameContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public String getMessageContact() {
        return messageContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public void setMessageContact(String messageContact) {
        this.messageContact = messageContact;
    }
}
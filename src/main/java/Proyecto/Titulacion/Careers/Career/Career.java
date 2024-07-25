package Proyecto.Titulacion.Careers.Career;

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
public class Career extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCareer;

    @Column(length = 100)
    private String nameCareer;

    @Column(length = 200)
    private String descriptionCareer;

    public Career() {
    }

    public long getIdCareer() {
        return idCareer;
    }

    public String getNameCareer() {
        return nameCareer;
    }

    public String getDescriptionCareer() {
        return descriptionCareer;
    }

    public void setIdCareer(long idCareer) {
        this.idCareer = idCareer;
    }

    public void setNameCareer(String nameCareer) {
        this.nameCareer = nameCareer;
    }

    public void setDescriptionCareer(String descriptionCareer) {
        this.descriptionCareer = descriptionCareer;
    }
}

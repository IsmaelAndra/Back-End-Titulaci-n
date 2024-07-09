package Proyecto.Titulacion.Magazines.Magazine;

import Proyecto.Titulacion.Careers.Career.Career;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMagazine;

    @Column(length = 100)
    private String nameMagazine;

    @Column(length = 300)
    private String descriptionMagazine;

    @ManyToOne
    @JoinColumn(name = "idCareer", referencedColumnName="idCareer")
    private Career career;

    public long getIdMagazine() {
        return idMagazine;
    }

    public String getNameMagazine() {
        return nameMagazine;
    }

    public String getDescriptionMagazine() {
        return descriptionMagazine;
    }

    public Career getCareer() {
        return career;
    }

    public void setIdMagazine(long idMagazine) {
        this.idMagazine = idMagazine;
    }

    public void setNameMagazine(String nameMagazine) {
        this.nameMagazine = nameMagazine;
    }

    public void setDescriptionMagazine(String descriptionMagazine) {
        this.descriptionMagazine = descriptionMagazine;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
}

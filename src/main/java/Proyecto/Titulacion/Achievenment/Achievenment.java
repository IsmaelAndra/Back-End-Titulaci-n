package Proyecto.Titulacion.Achievenment;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.Careers.Career.Career;
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
public class Achievenment extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAchievement;

    @Column(length = 100)
    private String nameAchievement;

    @Column(length = 500)
    private String descriptionAchievement;

    @ManyToOne
    @JoinColumn(name = "idCareer", referencedColumnName = "idCareer")
    private Career career;

    public long getIdAchievement() {
        return idAchievement;
    }

    public String getNameAchievement() {
        return nameAchievement;
    }

    public String getDescriptionAchievement() {
        return descriptionAchievement;
    }

    public Career getCareer() {
        return career;
    }

    public void setIdAchievement(long idAchievement) {
        this.idAchievement = idAchievement;
    }

    public void setNameAchievement(String nameAchievement) {
        this.nameAchievement = nameAchievement;
    }

    public void setDescriptionAchievement(String descriptionAchievement) {
        this.descriptionAchievement = descriptionAchievement;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
}

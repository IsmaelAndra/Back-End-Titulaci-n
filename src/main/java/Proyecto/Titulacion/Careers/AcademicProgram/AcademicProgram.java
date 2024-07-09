package Proyecto.Titulacion.Careers.AcademicProgram;

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
public class AcademicProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAcademicProgram;

    @Column(length = 100)
    private String nameAcademicProgram;

    @Column(length = 300)
    private String descriptionAcademicProgram;

    @ManyToOne
    @JoinColumn(name = "idCareer", referencedColumnName="idCareer")
    private Career career;

    public long getIdAcademicProgram() {
        return idAcademicProgram;
    }

    public String getNameAcademicProgram() {
        return nameAcademicProgram;
    }

    public String getDescriptionAcademicProgram() {
        return descriptionAcademicProgram;
    }

    public Career getCareer() {
        return career;
    }

    public void setIdAcademicProgram(long idAcademicProgram) {
        this.idAcademicProgram = idAcademicProgram;
    }

    public void setNameAcademicProgram(String nameAcademicProgram) {
        this.nameAcademicProgram = nameAcademicProgram;
    }

    public void setDescriptionAcademicProgram(String descriptionAcademicProgram) {
        this.descriptionAcademicProgram = descriptionAcademicProgram;
    }

    public void setCareer(Career career) {
        this.career = career;
    }    
}

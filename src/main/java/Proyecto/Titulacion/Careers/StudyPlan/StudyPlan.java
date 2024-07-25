package Proyecto.Titulacion.Careers.StudyPlan;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.Careers.AcademicProgram.AcademicProgram;
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
public class StudyPlan extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStudyPlan;

    @Column(length = 100)
    private String nameStudyPlan;

    @Column(length = 200)
    private String descriptionStudyPlan;

    @ManyToOne
    @JoinColumn(name = "idAcademicProgram", referencedColumnName = "idAcademicProgram")
    private AcademicProgram academicProgram;

    public long getIdStudyPlan() {
        return idStudyPlan;
    }

    public String getNameStudyPlan() {
        return nameStudyPlan;
    }

    public String getDescriptionStudyPlan() {
        return descriptionStudyPlan;
    }

    public AcademicProgram getAcademicProgram() {
        return academicProgram;
    }

    public void setIdStudyPlan(long idStudyPlan) {
        this.idStudyPlan = idStudyPlan;
    }

    public void setNameStudyPlan(String nameStudyPlan) {
        this.nameStudyPlan = nameStudyPlan;
    }

    public void setDescriptionStudyPlan(String descriptionStudyPlan) {
        this.descriptionStudyPlan = descriptionStudyPlan;
    }

    public void setAcademicProgram(AcademicProgram academicProgram) {
        this.academicProgram = academicProgram;
    }
}

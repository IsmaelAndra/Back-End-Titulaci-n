package Proyecto.Titulacion.Careers.StudyPlan;

import Proyecto.Titulacion.Careers.AcademicProgram.AcademicProgram;
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
public class StudyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_study_plan;
    @Column(length = 100)
    private String name_study_plan;
    @Column(length = 200)
    private String description_study_plan;

    @ManyToOne
    @JoinColumn(name = "id_academic_program", referencedColumnName="id_academic_program")
    private AcademicProgram academicProgram;
            
}

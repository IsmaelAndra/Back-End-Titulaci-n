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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_academic_program;
    @Column(length = 100)
    private String name_academic_program;
    @Column(length = 300)
    private String description_academic_program;

    @ManyToOne
    @JoinColumn(name = "id_career", referencedColumnName="id_career")
    private Career career;
            
}

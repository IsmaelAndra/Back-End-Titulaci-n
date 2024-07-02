package Proyecto.Titulacion.Projects.StudentProject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
        
@Data
@Entity
public class StudentProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_student_proyect;
    @Column(length = 100)
    private String name_student_proyect;
    @Column(length = 200)
    private String description_student_proyect;
            
}

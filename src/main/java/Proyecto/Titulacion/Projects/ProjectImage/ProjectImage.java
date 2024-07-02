package Proyecto.Titulacion.Projects.ProjectImage;

import Proyecto.Titulacion.Projects.StudentProject.StudentProject;
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
public class ProjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_proyect_image;
    @Column(length = 100)
    private String url_proyect_image;

    @ManyToOne
    @JoinColumn(name = "id_student_proyect", referencedColumnName="id_student_proyect")
    private StudentProject studentProyect;
            
}

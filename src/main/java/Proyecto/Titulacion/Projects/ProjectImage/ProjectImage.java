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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProjectImage;

    @Column(length = 100)
    private String urlProjectImage;

    @ManyToOne
    @JoinColumn(name = "idStudentProject", referencedColumnName="idStudentProject")
    private StudentProject studentProyect;

    public long getIdProjectImage() {
        return idProjectImage;
    }

    public String getUrlProjectImage() {
        return urlProjectImage;
    }

    public StudentProject getStudentProyect() {
        return studentProyect;
    }

    public void setIdProjectImage(long idProjectImage) {
        this.idProjectImage = idProjectImage;
    }

    public void setUrlProjectImage(String urlProjectImage) {
        this.urlProjectImage = urlProjectImage;
    }

    public void setStudentProyect(StudentProject studentProyect) {
        this.studentProyect = studentProyect;
    }        
}

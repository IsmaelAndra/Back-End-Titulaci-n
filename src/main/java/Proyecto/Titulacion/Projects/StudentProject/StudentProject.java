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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStudentProject;

    @Column(length = 100)
    private String nameStudentProject;

    @Column(length = 200)
    private String descriptionStudentProject;

    public long getIdStudentProject() {
        return idStudentProject;
    }

    public String getNameStudentProject() {
        return nameStudentProject;
    }

    public String getDescriptionStudentProject() {
        return descriptionStudentProject;
    }

    public void setIdStudentProject(long idStudentProject) {
        this.idStudentProject = idStudentProject;
    }

    public void setNameStudentProject(String nameStudentProject) {
        this.nameStudentProject = nameStudentProject;
    }

    public void setDescriptionStudentProject(String descriptionStudentProject) {
        this.descriptionStudentProject = descriptionStudentProject;
    }
}

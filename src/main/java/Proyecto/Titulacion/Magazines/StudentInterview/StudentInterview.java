package Proyecto.Titulacion.Magazines.StudentInterview;

import Proyecto.Titulacion.Magazines.Magazine.Magazine;
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
public class StudentInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStudentInterview;

    @Column(length = 100)
    private String nameStudentInterview;

    @Column(length = 300)
    private String contentStudentInterview;
    
    @ManyToOne
    @JoinColumn(name = "idMagazine", referencedColumnName="idMagazine")
    private Magazine magazine;

    public long getIdStudentInterview() {
        return idStudentInterview;
    }

    public String getNameStudentInterview() {
        return nameStudentInterview;
    }

    public String getContentStudentInterview() {
        return contentStudentInterview;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setIdStudentInterview(long idStudentInterview) {
        this.idStudentInterview = idStudentInterview;
    }

    public void setNameStudentInterview(String nameStudentInterview) {
        this.nameStudentInterview = nameStudentInterview;
    }

    public void setContentStudentInterview(String contentStudentInterview) {
        this.contentStudentInterview = contentStudentInterview;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
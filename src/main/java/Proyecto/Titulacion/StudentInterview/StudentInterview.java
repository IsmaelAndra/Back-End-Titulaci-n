package Proyecto.Titulacion.StudentInterview;

import Proyecto.Titulacion.Magazine.Magazine;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_student_interview;
    @Column(length = 100)
    private String name_student_interview;
    @Column(length = 300)
    private String content_student_interview;
    
    
    @ManyToOne
    @JoinColumn(name = "id_magazine", referencedColumnName="id_magazine")
    private Magazine magazine;
}

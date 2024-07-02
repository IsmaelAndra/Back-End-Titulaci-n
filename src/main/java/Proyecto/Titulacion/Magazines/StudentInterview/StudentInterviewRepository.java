package Proyecto.Titulacion.Magazines.StudentInterview;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface StudentInterviewRepository extends CrudRepository<StudentInterview, Long>{
    List<StudentInterview> findAll();
}

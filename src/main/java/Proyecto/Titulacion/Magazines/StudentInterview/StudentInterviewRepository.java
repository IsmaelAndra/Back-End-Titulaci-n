package Proyecto.Titulacion.Magazines.StudentInterview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface StudentInterviewRepository extends JpaRepository<StudentInterview, Long>{
    Page<StudentInterview> findByNameStudentInterviewContaining(String nameStudentInterview, Pageable pageable);
}

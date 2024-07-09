package Proyecto.Titulacion.Careers.AcademicProgram;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Long>{
    Page<AcademicProgram> findByNameAcademicProgramContaining(String nameAcademicProgram, Pageable pageable);
}

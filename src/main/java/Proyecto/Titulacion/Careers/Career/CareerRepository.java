package Proyecto.Titulacion.Careers.Career;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface CareerRepository extends JpaRepository<Career, Long>{
    Page<Career> findByNameCareerContaining(String nameCareer, Pageable pageable);
}

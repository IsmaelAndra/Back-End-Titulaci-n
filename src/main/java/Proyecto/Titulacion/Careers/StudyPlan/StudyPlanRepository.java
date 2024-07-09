package Proyecto.Titulacion.Careers.StudyPlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long>{
    Page<StudyPlan> findByNameStudyPlanContaining(String nameStudyPlan, Pageable pageable);
}

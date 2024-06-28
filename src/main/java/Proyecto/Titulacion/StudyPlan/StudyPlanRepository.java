package Proyecto.Titulacion.StudyPlan;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface StudyPlanRepository extends CrudRepository<StudyPlan, Long>{
    List<StudyPlan> findAll();
}

package Proyecto.Titulacion.StudyPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class StudyPlanService {
    @Autowired
    StudyPlanRepository repository;
    
    public StudyPlan save( StudyPlan entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public StudyPlan findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<StudyPlan> findAll(){
        return repository.findAll();
    }
    
}

package Proyecto.Titulacion.Careers.StudyPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class StudyPlanService {
    @Autowired
    StudyPlanRepository repository;
    
    public StudyPlan save( StudyPlan entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idStudyPlan ){
        repository.deleteById(idStudyPlan);
    }
    
    public StudyPlan findById(Long idStudyPlan){
        return repository.findById(idStudyPlan).orElse(null);
    }
    
    public List<StudyPlan> findAll(){
        return repository.findAll();
    }
    
    public Page<StudyPlan> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<StudyPlan> findByNameStudyPlan(String nameStudyPlan, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameStudyPlanContaining(nameStudyPlan, pageable);
    }
}

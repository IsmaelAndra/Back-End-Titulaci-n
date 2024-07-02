package Proyecto.Titulacion.Magazines.StudentInterview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class StudentInterviewService {
    @Autowired
    StudentInterviewRepository repository;
    
    public StudentInterview save( StudentInterview entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public StudentInterview findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<StudentInterview> findAll(){
        return repository.findAll();
    }
    
}

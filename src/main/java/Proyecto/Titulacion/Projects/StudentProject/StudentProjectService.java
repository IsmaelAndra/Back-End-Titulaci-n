package Proyecto.Titulacion.Projects.StudentProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class StudentProjectService {
    @Autowired
    StudentProjectRepository repository;
    
    public StudentProject save( StudentProject entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public StudentProject findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<StudentProject> findAll(){
        return repository.findAll();
    }
    
}

package Proyecto.Titulacion.StudentProyect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class StudentProyectService {
    @Autowired
    StudentProyectRepository repository;
    
    public StudentProyect save( StudentProyect entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public StudentProyect findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<StudentProyect> findAll(){
        return repository.findAll();
    }
    
}

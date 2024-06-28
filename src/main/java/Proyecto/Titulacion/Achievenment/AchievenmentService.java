package Proyecto.Titulacion.Achievenment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class AchievenmentService {
    @Autowired
    AchievenmentRepository repository;
    
    public Achievenment save( Achievenment entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Achievenment findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Achievenment> findAll(){
        return repository.findAll();
    }
    
}

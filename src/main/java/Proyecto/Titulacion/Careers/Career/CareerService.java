package Proyecto.Titulacion.Careers.Career;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class CareerService {
    @Autowired
    CareerRepository repository;
    
    public Career save( Career entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Career findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Career> findAll(){
        return repository.findAll();
    }
    
}

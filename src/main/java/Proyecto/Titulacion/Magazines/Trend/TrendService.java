package Proyecto.Titulacion.Magazines.Trend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class TrendService {
    @Autowired
    TrendRepository repository;
    
    public Trend save( Trend entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Trend findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Trend> findAll(){
        return repository.findAll();
    }
    
}
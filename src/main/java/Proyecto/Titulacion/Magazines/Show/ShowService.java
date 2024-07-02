package Proyecto.Titulacion.Magazines.Show;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ShowService {
    @Autowired
    ShowRepository repository;
    
    public Show save( Show entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Show findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Show> findAll(){
        return repository.findAll();
    }
    
}

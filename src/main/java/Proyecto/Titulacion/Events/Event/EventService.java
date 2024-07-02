package Proyecto.Titulacion.Events.Event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class EventService {
    @Autowired
    EventRepository repository;
    
    public Event save( Event entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Event findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Event> findAll(){
        return repository.findAll();
    }
    
}

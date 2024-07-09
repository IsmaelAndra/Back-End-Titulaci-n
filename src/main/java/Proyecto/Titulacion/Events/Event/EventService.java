package Proyecto.Titulacion.Events.Event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class EventService {
    @Autowired
    EventRepository repository;
    
    public Event save( Event entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idEvent ){
        repository.deleteById(idEvent);
    }
    
    public Event findById(Long idEvent){
        return repository.findById(idEvent).orElse(null);
    }
    
    public List<Event> findAll(){
        return repository.findAll();
    }
    
    public Page<Event> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Event> findByTitleEvent(String titleEvent, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleEventContaining(titleEvent, pageable);
    }
}

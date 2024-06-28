package Proyecto.Titulacion.EventGallery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class EventGalleryService {
    @Autowired
    EventGalleryRepository repository;
    
    public EventGallery save( EventGallery entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public EventGallery findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<EventGallery> findAll(){
        return repository.findAll();
    }
    
}

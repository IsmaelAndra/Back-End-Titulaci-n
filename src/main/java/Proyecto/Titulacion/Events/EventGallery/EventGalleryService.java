package Proyecto.Titulacion.Events.EventGallery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class EventGalleryService {
    @Autowired
    EventGalleryRepository repository;
    
    public EventGallery save( EventGallery entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idEventGallery ){
        repository.deleteById(idEventGallery);
    }
    
    public EventGallery findById(Long idEventGallery){
        return repository.findById(idEventGallery).orElse(null);
    }
    
    public List<EventGallery> findAll(){
        return repository.findAll();
    }
    
    public Page<EventGallery> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<EventGallery> findByTitleEventGallery(String titleEventGallery, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleEventGalleryContaining(titleEventGallery, pageable);
    }
}

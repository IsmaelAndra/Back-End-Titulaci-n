package Proyecto.Titulacion.EventGallery;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface EventGalleryRepository extends CrudRepository<EventGallery, Long>{
    List<EventGallery> findAll();
}

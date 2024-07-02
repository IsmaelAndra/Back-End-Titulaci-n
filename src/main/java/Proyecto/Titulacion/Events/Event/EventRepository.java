package Proyecto.Titulacion.Events.Event;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface EventRepository extends CrudRepository<Event, Long>{
    List<Event> findAll();
}

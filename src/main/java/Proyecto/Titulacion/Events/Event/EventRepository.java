package Proyecto.Titulacion.Events.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTitleEventContaining(String titleEvent, Pageable pageable);
}

package Proyecto.Titulacion.Events.EventGallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGalleryRepository extends JpaRepository<EventGallery, Long> {
    Page<EventGallery> findByTitleEventGalleryContaining(String titleEventGallery, Pageable pageable);
}

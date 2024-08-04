package Proyecto.Titulacion.Magazines.Show;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Page<Show> findByTitleShowContaining(String titleShow, Pageable pageable);
}

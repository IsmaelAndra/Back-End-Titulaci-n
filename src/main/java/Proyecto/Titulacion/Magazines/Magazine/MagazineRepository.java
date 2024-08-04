package Proyecto.Titulacion.Magazines.Magazine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Page<Magazine> findByNameMagazineContaining(String nameMagazine, Pageable pageable);
}

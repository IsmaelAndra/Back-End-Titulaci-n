package Proyecto.Titulacion.Magazines.Trend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface TrendRepository extends JpaRepository<Trend, Long>{
    Page<Trend> findByTitleTrendContaining(String titleTrend, Pageable pageable);
}

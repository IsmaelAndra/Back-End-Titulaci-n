package Proyecto.Titulacion.Trend;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface TrendRepository extends CrudRepository<Trend, Long>{
    List<Trend> findAll();
}

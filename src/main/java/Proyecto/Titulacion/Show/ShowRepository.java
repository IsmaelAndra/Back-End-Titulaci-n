package Proyecto.Titulacion.Show;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface ShowRepository extends CrudRepository<Show, Long>{
    List<Show> findAll();
}

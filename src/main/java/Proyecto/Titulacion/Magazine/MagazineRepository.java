package Proyecto.Titulacion.Magazine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface MagazineRepository extends CrudRepository<Magazine, Long>{
    List<Magazine> findAll();
}

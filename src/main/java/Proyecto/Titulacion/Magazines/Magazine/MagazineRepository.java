package Proyecto.Titulacion.Magazines.Magazine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface MagazineRepository extends CrudRepository<Magazine, Long>{
    List<Magazine> findAll();
}

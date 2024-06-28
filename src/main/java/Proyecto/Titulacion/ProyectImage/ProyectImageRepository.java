package Proyecto.Titulacion.ProyectImage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface ProyectImageRepository extends CrudRepository<ProyectImage, Long>{
    List<ProyectImage> findAll();
}

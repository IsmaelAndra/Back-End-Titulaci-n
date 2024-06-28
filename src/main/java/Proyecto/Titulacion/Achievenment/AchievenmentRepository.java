package Proyecto.Titulacion.Achievenment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface AchievenmentRepository extends CrudRepository<Achievenment, Long>{
    List<Achievenment> findAll();
}

package Proyecto.Titulacion.StudentProyect;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface StudentProyectRepository extends CrudRepository<StudentProyect, Long>{
    List<StudentProyect> findAll();
}

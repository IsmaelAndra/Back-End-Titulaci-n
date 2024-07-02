package Proyecto.Titulacion.Projects.StudentProject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface StudentProjectRepository extends CrudRepository<StudentProject, Long>{
    List<StudentProject> findAll();
}

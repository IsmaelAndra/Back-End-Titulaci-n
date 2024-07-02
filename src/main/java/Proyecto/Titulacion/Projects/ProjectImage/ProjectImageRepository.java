package Proyecto.Titulacion.Projects.ProjectImage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface ProjectImageRepository extends CrudRepository<ProjectImage, Long>{
    List<ProjectImage> findAll();
}
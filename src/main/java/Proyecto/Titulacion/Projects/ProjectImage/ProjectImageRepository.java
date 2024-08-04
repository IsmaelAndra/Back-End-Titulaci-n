package Proyecto.Titulacion.Projects.ProjectImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    @SuppressWarnings("null")
    List<ProjectImage> findAll();
}
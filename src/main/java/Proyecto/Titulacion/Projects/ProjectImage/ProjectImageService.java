package Proyecto.Titulacion.Projects.ProjectImage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectImageService {
    @Autowired
    ProjectImageRepository repository;

    public ProjectImage save(ProjectImage entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idProjectImage) {
        repository.deleteById(idProjectImage);
    }

    public ProjectImage findById(Long idProjectImage) {
        return repository.findById(idProjectImage).orElse(null);
    }

    public List<ProjectImage> findAll() {
        return repository.findAll();
    }
}

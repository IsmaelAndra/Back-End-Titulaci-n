package Proyecto.Titulacion.Projects.StudentProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class StudentProjectService {
    @Autowired
    StudentProjectRepository repository;

    public StudentProject save(StudentProject entity) {
        return repository.save(entity);
    }

    public StudentProject findById(Long idStudentProject) {
        return repository.findById(idStudentProject).orElse(null);
    }

    public void deleteById(Long idStudentProject) {
        repository.deleteById(idStudentProject);
    }

    public List<StudentProject> findAll() {
        return repository.findAll();
    }

    public Page<StudentProject> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<StudentProject> findByNameStudentProject(String nameStudentProject, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameStudentProjectContaining(nameStudentProject, pageable);
    }
}

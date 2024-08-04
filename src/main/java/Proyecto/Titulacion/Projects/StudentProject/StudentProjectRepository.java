package Proyecto.Titulacion.Projects.StudentProject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProjectRepository extends JpaRepository<StudentProject, Long> {
    Page<StudentProject> findByNameStudentProjectContaining(String nameStudentProject, Pageable pageable);
}

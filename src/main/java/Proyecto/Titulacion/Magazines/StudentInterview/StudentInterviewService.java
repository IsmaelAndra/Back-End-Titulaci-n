package Proyecto.Titulacion.Magazines.StudentInterview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentInterviewService {
    @Autowired
    StudentInterviewRepository repository;

    public StudentInterview save(StudentInterview entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idStudentInterview) {
        repository.deleteById(idStudentInterview);
    }

    public StudentInterview findById(Long idStudentInterview) {
        return repository.findById(idStudentInterview).orElse(null);
    }

    public List<StudentInterview> findAll() {
        return repository.findAll();
    }

    public Page<StudentInterview> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<StudentInterview> findByNameStudentInterview(String nameStudentInterview, int page, int size,
            String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameStudentInterviewContaining(nameStudentInterview, pageable);
    }
}

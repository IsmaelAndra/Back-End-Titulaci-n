package Proyecto.Titulacion.Achievenment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AchievenmentService {
    @Autowired
    AchievenmentRepository repository;

    public Achievenment save(Achievenment entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idAchievement) {
        repository.deleteById(idAchievement);
    }

    public Achievenment findById(Long idAchievement) {
        return repository.findById(idAchievement).orElse(null);
    }

    public List<Achievenment> findAll() {
        return repository.findAll();
    }

    public Page<Achievenment> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Achievenment> findByNameAchievement(String nameAchieviement, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameAchievementContaining(nameAchieviement, pageable);
    }
}

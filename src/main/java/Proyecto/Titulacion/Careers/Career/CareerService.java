package Proyecto.Titulacion.Careers.Career;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CareerService {
    @Autowired
    CareerRepository repository;

    public Career save(Career entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idCareer) {
        repository.deleteById(idCareer);
    }

    public Optional<Career> findById(Long id) {
        return repository.findById(id);
    }

    public Career findByNameCareer(String nameCareer) {
        return repository.findByNameCareer(nameCareer)
                .orElseThrow(() -> new UsernameNotFoundException("career not found with nameCareer: " + nameCareer));
    }

    public List<Career> findAll() {
        return repository.findAll();
    }

    public Page<Career> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Career> findByNameCareer(String nameCareer, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameCareerContaining(nameCareer, pageable);
    }
}

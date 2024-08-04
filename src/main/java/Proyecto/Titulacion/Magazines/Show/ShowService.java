package Proyecto.Titulacion.Magazines.Show;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ShowService {
    @Autowired
    ShowRepository repository;

    public Show save(Show entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idShow) {
        repository.deleteById(idShow);
    }

    public Show findById(Long idShow) {
        return repository.findById(idShow).orElse(null);
    }

    public List<Show> findAll() {
        return repository.findAll();
    }

    public Page<Show> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Show> findByTitleShow(String titleShow, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleShowContaining(titleShow, pageable);
    }
}

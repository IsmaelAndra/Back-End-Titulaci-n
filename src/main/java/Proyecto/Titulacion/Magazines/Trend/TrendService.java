package Proyecto.Titulacion.Magazines.Trend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TrendService {
    @Autowired
    TrendRepository repository;

    public Trend save(Trend entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idTrend) {
        repository.deleteById(idTrend);
    }

    public Trend findById(Long idTrend) {
        return repository.findById(idTrend).orElse(null);
    }

    public List<Trend> findAll() {
        return repository.findAll();
    }

    public Page<Trend> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Trend> findByTitleTrend(String titleTrend, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleTrendContaining(titleTrend, pageable);
    }
}
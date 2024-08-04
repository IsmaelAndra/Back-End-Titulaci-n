package Proyecto.Titulacion.Magazines.Magazine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class MagazineService {
    @Autowired
    MagazineRepository repository;

    public Magazine save(Magazine entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idMagazine) {
        repository.deleteById(idMagazine);
    }

    public Optional<Magazine> findById(Long id) {
        return repository.findById(id);
    }

    public List<Magazine> findAll() {
        return repository.findAll();
    }

    public Page<Magazine> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Magazine> findByNameMagazine(String nameMagazine, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameMagazineContaining(nameMagazine, pageable);
    }

    public Map<String, Long> getMagazineStats(String period) {
        List<Magazine> magazines = repository.findAll();

        switch (period) {
            case "diaria":
                return magazines.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().toLocalDate().plusDays(1).toString(),
                                Collectors.counting()));
            case "mensual":
                return magazines.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().getYear() + "-"
                                        + String.format("%02d", p.getCreatedAt().getMonthValue() + 1),
                                Collectors.counting()));
            case "anual":
                return magazines.stream()
                        .collect(Collectors.groupingBy(
                                p -> String.valueOf(p.getCreatedAt().getYear() + 1),
                                Collectors.counting()));
            default:
                throw new IllegalArgumentException("Periodo Invalido: " + period);
        }
    }
}

package Proyecto.Titulacion.Events.Event;

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
public class EventService {
    @Autowired
    EventRepository repository;

    public Event save(Event entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idEvent) {
        repository.deleteById(idEvent);
    }

    public Optional<Event> findById(Long id) {
        return repository.findById(id);
    }

    public List<Event> findAll() {
        return repository.findAll();
    }

    public Page<Event> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Event> findByTitleEvent(String titleEvent, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleEventContaining(titleEvent, pageable);
    }

    public Map<String, Long> getEventStats(String period) {
        List<Event> events = repository.findAll();

        switch (period) {
            case "diaria":
                return events.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().toLocalDate().plusDays(1).toString(),
                                Collectors.counting()));
            case "mensual":
                return events.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().getYear() + "-"
                                        + String.format("%02d", p.getCreatedAt().getMonthValue() + 1),
                                Collectors.counting()));
            case "anual":
                return events.stream()
                        .collect(Collectors.groupingBy(
                                p -> String.valueOf(p.getCreatedAt().getYear() + 1),
                                Collectors.counting()));
            default:
                throw new IllegalArgumentException("Periodo Invalido: " + period);
        }
    }
}

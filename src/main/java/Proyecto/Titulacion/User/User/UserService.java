package Proyecto.Titulacion.User.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public void deleteByID(long idUser) {
        userRepository.deleteById(idUser);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable);
    }

    public Page<User> findByNameUser(String nameUser, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findByNameUserContaining(nameUser, pageable);
    }

    public Map<String, Long> getUserStats(String period) {
        List<User> users = userRepository.findAll();

        switch (period) {
            case "diaria":
                return users.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().toLocalDate().plusDays(1).toString(),
                                Collectors.counting()));
            case "mensual":
                return users.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().getYear() + "-"
                                        + String.format("%02d", p.getCreatedAt().getMonthValue() + 1),
                                Collectors.counting()));
            case "anual":
                return users.stream()
                        .collect(Collectors.groupingBy(
                                p -> String.valueOf(p.getCreatedAt().getYear() + 1),
                                Collectors.counting()));
            default:
                throw new IllegalArgumentException("Periodo Invalido: " + period);
        }
    }
}

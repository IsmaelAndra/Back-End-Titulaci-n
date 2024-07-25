package Proyecto.Titulacion.User.User;

import java.util.List;

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

    public User findById(long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    public void deleteByID(long idUser) {
        userRepository.deleteById(idUser);
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
}

package Proyecto.Titulacion.User.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByVerificationCode(String verificationCode);

    Page<User> findByNameUserContaining(String nameUser, Pageable pageable);
}

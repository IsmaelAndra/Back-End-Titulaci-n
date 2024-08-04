package Proyecto.Titulacion.User.Rol;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNameRol(String nameRol);

    Page<Rol> findByNameRolContaining(String nameRol, Pageable pageable);
}

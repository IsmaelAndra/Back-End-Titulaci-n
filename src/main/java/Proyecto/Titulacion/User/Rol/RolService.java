package Proyecto.Titulacion.User.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol save(Rol entity) {
        return rolRepository.save(entity);
    }

    public Rol findById(long idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    public void deleteByID(long idRol) {
        rolRepository.deleteById(idRol);
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Page<Rol> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return rolRepository.findAll(pageable);
    }

    public Page<Rol> findByNameRol(String nameRol, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return rolRepository.findByNameRolContaining(nameRol, pageable);
    }
}

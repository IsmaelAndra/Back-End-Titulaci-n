package Proyecto.Titulacion.Magazines.Magazine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class MagazineService {
    @Autowired
    MagazineRepository repository;
    
    public Magazine save( Magazine entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idMagazine ){
        repository.deleteById(idMagazine);
    }
    
    public Magazine findById(Long idMagazine){
        return repository.findById(idMagazine).orElse(null);
    }
    
    public List<Magazine> findAll(){
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
}

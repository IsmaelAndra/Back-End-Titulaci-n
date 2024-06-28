package Proyecto.Titulacion.Magazine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class MagazineService {
    @Autowired
    MagazineRepository repository;
    
    public Magazine save( Magazine entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Magazine findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Magazine> findAll(){
        return repository.findAll();
    }
    
}

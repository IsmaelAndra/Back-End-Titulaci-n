package Proyecto.Titulacion.ProyectImage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ProyectImageService {
    @Autowired
    ProyectImageRepository repository;
    
    public ProyectImage save( ProyectImage entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public ProyectImage findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<ProyectImage> findAll(){
        return repository.findAll();
    }
    
}

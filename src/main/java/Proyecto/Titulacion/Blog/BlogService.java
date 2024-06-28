package Proyecto.Titulacion.Blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class BlogService {
    @Autowired
    BlogRepository repository;
    
    public Blog save( Blog entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Blog findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Blog> findAll(){
        return repository.findAll();
    }
    
}

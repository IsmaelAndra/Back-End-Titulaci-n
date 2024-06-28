package Proyecto.Titulacion.CommentBlog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class CommentBlogService {
    @Autowired
    CommentBlogRepository repository;
    
    public CommentBlog save( CommentBlog entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public CommentBlog findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<CommentBlog> findAll(){
        return repository.findAll();
    }
    
}

package Proyecto.Titulacion.Products.ProductComment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ProductCommentService {
    @Autowired
    ProductCommentRepository repository;
    
    public ProductComment save( ProductComment entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public ProductComment findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<ProductComment> findAll(){
        return repository.findAll();
    }
    
}
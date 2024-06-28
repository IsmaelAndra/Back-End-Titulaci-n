package Proyecto.Titulacion.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ProductService {
    @Autowired
    ProductRepository repository;
    
    public Product save( Product entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public Product findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<Product> findAll(){
        return repository.findAll();
    }
    
}

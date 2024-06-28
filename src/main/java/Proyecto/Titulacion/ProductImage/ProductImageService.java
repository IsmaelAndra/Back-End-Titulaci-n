package Proyecto.Titulacion.ProductImage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository repository;
    
    public ProductImage save( ProductImage entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long id ){
        repository.deleteById(id);
    }
    
    public ProductImage findById(Long id){
        return repository.findById(id).orElse(null);
    }
    
    public List<ProductImage> findAll(){
        return repository.findAll();
    }
    
}

package Proyecto.Titulacion.Products.ProductImage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Proyecto.Titulacion.Products.Product.Product;
    
@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository repository;

    public ProductImage save(ProductImage entity) {
        return repository.save(entity);
    }
    
    public void deleteById( Long idProductImage ){
        repository.deleteById(idProductImage);
    }
    
    public ProductImage findById(Long idProductImage){
        return repository.findById(idProductImage).orElse(null);
    }
    
    public List<ProductImage> findAll(){
        return repository.findAll();
    }

    public void deleteByProduct(Product product) {
        List<ProductImage> images = repository.findByProduct(product);
        repository.deleteAll(images);
    }

    public List<ProductImage> findByProduct(Product product) {
        return repository.findByProduct(product);
    }
}

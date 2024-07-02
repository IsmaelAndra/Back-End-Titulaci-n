package Proyecto.Titulacion.Products.ProductImage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface ProductImageRepository extends CrudRepository<ProductImage, Long>{
    List<ProductImage> findAll();
}

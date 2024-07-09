package Proyecto.Titulacion.Products.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
   
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
    @SuppressWarnings("null")
    List<ProductImage> findAll();
}

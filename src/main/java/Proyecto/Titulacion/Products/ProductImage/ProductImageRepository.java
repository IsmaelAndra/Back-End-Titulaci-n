package Proyecto.Titulacion.Products.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Proyecto.Titulacion.Products.Product.Product;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @SuppressWarnings("null")
    List<ProductImage> findAll();

    List<ProductImage> findByProduct(Product product);
}

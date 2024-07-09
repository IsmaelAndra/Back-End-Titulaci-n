package Proyecto.Titulacion.Products.ProductComment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
    
@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long>{
    @SuppressWarnings("null")
    List<ProductComment> findAll();
}

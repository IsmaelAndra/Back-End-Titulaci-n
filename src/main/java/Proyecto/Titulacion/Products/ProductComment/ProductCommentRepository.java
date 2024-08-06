package Proyecto.Titulacion.Products.ProductComment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommentRepository extends CrudRepository<ProductComment, Long> {
    @SuppressWarnings("null")
    List<ProductComment> findAll();
    List<ProductComment> findByProduct_IdProduct(Long idProduct);
}

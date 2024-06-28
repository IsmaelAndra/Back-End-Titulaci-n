package Proyecto.Titulacion.ProductComment;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface ProductCommentRepository extends CrudRepository<ProductComment, Long>{
    List<ProductComment> findAll();
}

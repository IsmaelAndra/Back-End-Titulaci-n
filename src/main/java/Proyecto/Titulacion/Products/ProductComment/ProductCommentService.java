package Proyecto.Titulacion.Products.ProductComment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentService {
    @Autowired
    ProductCommentRepository repository;

    public ProductComment save(ProductComment entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idProductComment) {
        repository.deleteById(idProductComment);
    }

    public ProductComment findById(Long idProductComment) {
        return repository.findById(idProductComment).orElse(null);
    }

    public List<ProductComment> findByProductId(Long idProduct){
        return repository.findByProduct_IdProduct(idProduct);
    }

    public List<ProductComment> findAll() {
        return repository.findAll();
    }
}
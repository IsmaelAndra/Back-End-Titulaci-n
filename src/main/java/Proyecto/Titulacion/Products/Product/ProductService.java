package Proyecto.Titulacion.Products.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
@Service
public class ProductService {
    @Autowired
    ProductRepository repository;
    
    public Product save( Product entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idProduct ){
        repository.deleteById(idProduct);
    }
    
    public Product findById(Long idProduct){
        return repository.findById(idProduct).orElse(null);
    }
    
    public List<Product> findAll(){
        return repository.findAll();
    }
    
    public Page<Product> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Product> findByNameProduct(String nameProduct, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByNameProductContaining(nameProduct, pageable);
    }
}
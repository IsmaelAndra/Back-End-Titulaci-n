package Proyecto.Titulacion.Products.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
    
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
    
    public Optional<Product> findById(Long idProduct) {
        return repository.findById(idProduct);
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

    public Map<String, Long> getProductStats(String period) {
        List<Product> products = repository.findAll();
        
        switch (period) {
            case "diaria":
                return products.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().toLocalDate().plusDays(1).toString(),
                                Collectors.counting()
                        ));
            case "mensual":
                return products.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().getYear() + "-" + String.format("%02d", p.getCreatedAt().getMonthValue() + 1),
                                Collectors.counting()
                        ));
            case "anual":
                return products.stream()
                        .collect(Collectors.groupingBy(
                                p -> String.valueOf(p.getCreatedAt().getYear() + 1),
                                Collectors.counting()
                        ));
            default:
                throw new IllegalArgumentException("Periodo Invalido: " + period);
        }
    }    
}
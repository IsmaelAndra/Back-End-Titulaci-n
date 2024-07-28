package Proyecto.Titulacion.Blogs.Blog;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
    
@Service
public class BlogService {
    @Autowired
    BlogRepository repository;
    
    public Blog save( Blog entity ){
        return repository.save(entity);
    }
    
    public void deleteById( Long idBlog ){
        repository.deleteById(idBlog);
    }
    
    public Optional<Blog> findById(Long id){
        return repository.findById(id);
    }
    
    public List<Blog> findAll(){
        return repository.findAll();
    }
    
    public Page<Blog> findPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable);
    }

    public Page<Blog> findByTitleBlog(String TitleBlog, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findByTitleBlogContaining(TitleBlog, pageable);
    }

    public Map<String, Long> getBlogStats(String period) {
        List<Blog> blogs = repository.findAll();
        
        switch (period) {
            case "diaria":
                return blogs.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().toLocalDate().plusDays(1).toString(),
                                Collectors.counting()
                        ));
            case "mensual":
                return blogs.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getCreatedAt().getYear() + "-" + String.format("%02d", p.getCreatedAt().getMonthValue() + 1),
                                Collectors.counting()
                        ));
            case "anual":
                return blogs.stream()
                        .collect(Collectors.groupingBy(
                                p -> String.valueOf(p.getCreatedAt().getYear() + 1),
                                Collectors.counting()
                        ));
            default:
                throw new IllegalArgumentException("Periodo Invalido: " + period);
        }
    } 
}

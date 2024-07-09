package Proyecto.Titulacion.Blogs.Blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
    
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
    
    public Blog findById(Long idBlog){
        return repository.findById(idBlog).orElse(null);
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
}

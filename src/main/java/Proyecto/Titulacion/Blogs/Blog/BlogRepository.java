package Proyecto.Titulacion.Blogs.Blog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface BlogRepository extends CrudRepository<Blog, Long>{
    List<Blog> findAll();
}

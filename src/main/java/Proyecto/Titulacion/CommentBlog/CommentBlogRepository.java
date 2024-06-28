package Proyecto.Titulacion.CommentBlog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface CommentBlogRepository extends CrudRepository<CommentBlog, Long>{
    List<CommentBlog> findAll();
}

package Proyecto.Titulacion.Blogs.CommentBlog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
    
public interface CommentBlogRepository extends CrudRepository<CommentBlog, Long>{
    @SuppressWarnings("null")
    List<CommentBlog> findAll();
}

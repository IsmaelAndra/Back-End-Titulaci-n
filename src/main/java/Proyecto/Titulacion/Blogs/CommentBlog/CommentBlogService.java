package Proyecto.Titulacion.Blogs.CommentBlog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentBlogService {
    @Autowired
    CommentBlogRepository repository;

    public CommentBlog save(CommentBlog entity) {
        return repository.save(entity);
    }

    public void deleteById(Long idCommentBlog) {
        repository.deleteById(idCommentBlog);
    }

    public CommentBlog findById(long idCommentBlog) {
        return repository.findById(idCommentBlog).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public List<CommentBlog> findByBlogId(Long idBlog) {
        return repository.findByBlog_IdBlog(idBlog);
    }

    public List<CommentBlog> findAll() {
        return repository.findAll();
    }
}
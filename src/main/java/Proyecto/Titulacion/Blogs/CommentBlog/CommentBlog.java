package Proyecto.Titulacion.Blogs.CommentBlog;

import Proyecto.Titulacion.Blogs.Blog.Blog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
        
@Data
@Entity
public class CommentBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCommentBlog;

    @Column(length = 100)
    private String contentCommentBlog;

    @ManyToOne
    @JoinColumn(name = "idBlog", referencedColumnName = "idBlog")
    private Blog blog;

    public long getIdCommentBlog() {
        return idCommentBlog;
    }

    public String getContentCommentBlog() {
        return contentCommentBlog;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setIdCommentBlog(long idCommentBlog) {
        this.idCommentBlog = idCommentBlog;
    }

    public void setContentCommentBlog(String contentCommentBlog) {
        this.contentCommentBlog = contentCommentBlog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}

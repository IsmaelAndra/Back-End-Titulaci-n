package Proyecto.Titulacion.Blogs.CommentBlog;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.Blogs.Blog.Blog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CommentBlog extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCommentBlog;

    @Column(length = 100)
    private String contentCommentBlog;

    @Column(length = 100)
    private String usernameCommentBlog;

    @ManyToOne
    @JoinColumn(name = "idBlog", referencedColumnName = "idBlog")
    private Blog blog;

    public long getIdCommentBlog() {
        return idCommentBlog;
    }

    public String getContentCommentBlog() {
        return contentCommentBlog;
    }

    public String getUsernameCommentBlog() {
        return usernameCommentBlog;
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

    public void setUsernameCommentBlog(String usernameCommentBlog) {
        this.usernameCommentBlog = usernameCommentBlog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}

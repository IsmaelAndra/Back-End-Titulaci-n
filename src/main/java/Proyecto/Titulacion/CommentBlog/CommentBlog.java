package Proyecto.Titulacion.CommentBlog;

import Proyecto.Titulacion.Blog.Blog;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_comment_blog;
    @Column(length = 100)
    private String content_comment_blog;

    @ManyToOne
    @JoinColumn(name = "id_blog", referencedColumnName="id_blog")
    private Blog blog;
            
}

package Proyecto.Titulacion.Blogs.Blog;

import Proyecto.Titulacion.User.User.User;
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
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_blog;
    @Column(length = 100)
    private String title_blog;
    @Column(length = 100)
    private String subtitle_blog;
    @Column(length = 500)
    private String content_blog;
    @Column(length = 500)
    private String url_content_blog;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName="id_user")
    private User user;
            
}

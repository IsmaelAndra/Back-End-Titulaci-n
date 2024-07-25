package Proyecto.Titulacion.Blogs.Blog;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.User.User.User;
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
public class Blog extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBlog;

    @Column(length = 100)
    private String titleBlog;

    @Column(length = 100)
    private String subtitleBlog;

    @Column(length = 500)
    private String contentBlog;

    @Column(length = 500)
    private String url_contentBlog;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    private User user;

    public long getIdBlog() {
        return idBlog;
    }

    public String getTitleBlog() {
        return titleBlog;
    }

    public String getSubtitleBlog() {
        return subtitleBlog;
    }

    public String getContentBlog() {
        return contentBlog;
    }

    public String getUrl_contentBlog() {
        return url_contentBlog;
    }

    public User getUser() {
        return user;
    }

    public void setIdBlog(long idBlog) {
        this.idBlog = idBlog;
    }

    public void setTitleBlog(String titleBlog) {
        this.titleBlog = titleBlog;
    }

    public void setSubtitleBlog(String subtitleBlog) {
        this.subtitleBlog = subtitleBlog;
    }

    public void setContentBlog(String contentBlog) {
        this.contentBlog = contentBlog;
    }

    public void setUrl_contentBlog(String url_contentBlog) {
        this.url_contentBlog = url_contentBlog;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package Proyecto.Titulacion.Magazines.Show;

import Proyecto.Titulacion.Audit.Audit;
import Proyecto.Titulacion.Magazines.Magazine.Magazine;
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
public class Show extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idShow;
    @Column(length = 100)
    private String titleShow;
    @Column(length = 300)
    private String descriptionShow;
    @Column(length = 500)
    private String urlImageShow;

    @ManyToOne
    @JoinColumn(name = "idMagazine", referencedColumnName="idMagazine")
    private Magazine magazine;

    public long getIdShow() {
        return idShow;
    }

    public String getTitleShow() {
        return titleShow;
    }

    public String getDescriptionShow() {
        return descriptionShow;
    }

    public String getUrlImageShow() {
        return urlImageShow;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setIdShow(long idShow) {
        this.idShow = idShow;
    }

    public void setTitleShow(String titleShow) {
        this.titleShow = titleShow;
    }

    public void setDescriptionShow(String descriptionShow) {
        this.descriptionShow = descriptionShow;
    }

    public void setUrlImageShow(String urlImageShow) {
        this.urlImageShow = urlImageShow;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}

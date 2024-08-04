package Proyecto.Titulacion.Magazines.Trend;

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
public class Trend extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTrend;
    @Column(length = 100)
    private String titleTrend;
    @Column(length = 100)
    private String descriptionTrend;

    @ManyToOne
    @JoinColumn(name = "idMagazine", referencedColumnName = "idMagazine")
    private Magazine magazine;

    public long getIdTrend() {
        return idTrend;
    }

    public String getTitleTrend() {
        return titleTrend;
    }

    public String getDescriptionTrend() {
        return descriptionTrend;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setIdTrend(long idTrend) {
        this.idTrend = idTrend;
    }

    public void setTitleTrend(String titleTrend) {
        this.titleTrend = titleTrend;
    }

    public void setDescriptionTrend(String descriptionTrend) {
        this.descriptionTrend = descriptionTrend;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
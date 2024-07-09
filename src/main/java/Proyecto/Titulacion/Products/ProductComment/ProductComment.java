package Proyecto.Titulacion.Products.ProductComment;

import Proyecto.Titulacion.Products.Product.Product;
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
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProductComment;
    
    @Column(length = 100)
    private String contentProductComment;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName="idProduct")
    private Product product;

    public long getIdProductComment() {
        return idProductComment;
    }

    public String getContentProductComment() {
        return contentProductComment;
    }

    public Product getProduct() {
        return product;
    }

    public void setIdProductComment(long idProductComment) {
        this.idProductComment = idProductComment;
    }

    public void setContentProductComment(String contentProductComment) {
        this.contentProductComment = contentProductComment;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

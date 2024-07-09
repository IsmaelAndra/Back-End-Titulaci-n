package Proyecto.Titulacion.Products.ProductImage;

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
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProductImage;
    @Column(length = 255)
    private String urlProductImage;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName="idProduct")
    private Product product;

    public long getIdProductImage() {
        return idProductImage;
    }

    public String getUrlProductImage() {
        return urlProductImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setIdProductImage(long idProductImage) {
        this.idProductImage = idProductImage;
    }

    public void setUrlProductImage(String urlProductImage) {
        this.urlProductImage = urlProductImage;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_product_image;
    @Column(length = 255)
    private String url_product_image;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName="id_product")
    private Product product;
            
}

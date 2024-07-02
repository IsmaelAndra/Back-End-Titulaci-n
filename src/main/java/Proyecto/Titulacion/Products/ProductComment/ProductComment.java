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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_comment_product;
    @Column(length = 100)
    private String content_comment_product;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName="id_product")
    private Product product;
            
}

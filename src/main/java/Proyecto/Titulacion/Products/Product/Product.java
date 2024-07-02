package Proyecto.Titulacion.Products.Product;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_product;
    @Column(length = 50)
    private String name_product;
    @Column(length = 100)
    private String description_product;
    @Column(length = 10)
    private Double price_product;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName="id_user")
    private User user;
            
}

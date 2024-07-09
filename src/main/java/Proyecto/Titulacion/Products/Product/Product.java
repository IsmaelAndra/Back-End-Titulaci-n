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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct;

    @Column(length = 50)
    private String nameProduct;

    @Column(length = 100)
    private String descriptionProduct;

    @Column(length = 10)
    private Double priceProduct;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    private User user;

    public long getIdProduct() {
        return idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public User getUser() {
        return user;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        if (priceProduct < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.priceProduct = priceProduct;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
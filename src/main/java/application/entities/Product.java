package application.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String category;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @Column
    private String description;

    @Getter
    @Setter
    @Column(nullable = false)
    private int unit_price;

    @Getter
    @Setter
    @Column
    private int inventory;

    @Getter
    @Setter
    @Column
    private String payment_options;

    @Getter
    @Setter
    @Column
    private String delivery_options;

    @Getter
    @Setter
    @Column(nullable = false)
    private int merchantId;





    public Product() {
    }

    public Product(String category, String name, String description, int unit_price, int inventory, String payment_options, String delivery_options, int merchantId) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.inventory = inventory;
        this.payment_options = payment_options;
        this.delivery_options = delivery_options;
        this.merchantId = merchantId;
    }
}

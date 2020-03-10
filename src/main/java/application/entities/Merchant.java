package application.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String type;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @Column
    private String owner_name;

    @Getter
    @Setter
    @Column
    private String address;

    @Getter
    @Setter
    @Column
    private String phone_number;

    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @Column
    private String password;

    @Getter
    @Setter
    @Column
    private String token;

    @Getter
    @Column
    private String role = "ROLE_MERCHANT";

    @Getter
    @Setter
    @Column
    private boolean isActive = false;


    public Merchant() {
    }


    public Merchant(String type, String name, String owner_name, String address, String phone_number, String email, String password, String role, boolean isActive) {
        this.type = type;
        this.name = name;
        this.owner_name = owner_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }
}



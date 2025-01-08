package com.bella.BellaBoutique.model.users;

import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.model.cart.ShoppingCart;
import com.bella.BellaBoutique.model.orders.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "address_line")
    private String addressLine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private UserPhoto userPhoto;

    @Getter
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonIgnoreProperties("user")
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Product> products;

    public void addAuthority(Authority authority) {
        authority.setUser(this);
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        authority.setUser(null);
        this.authorities.remove(authority);
    }

}

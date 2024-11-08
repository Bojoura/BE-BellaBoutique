package com.bella.BellaBoutique.model.users;

import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.model.reviews.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToOne
    UserPhoto userPhoto;

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

    public void addAuthority(Authority authority) {
        authority.setUser(this);
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        authority.setUser(null);
        this.authorities.remove(authority);
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;
}

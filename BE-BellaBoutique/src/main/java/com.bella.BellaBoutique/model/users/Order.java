package com.bella.BellaBoutique.model.users;

import com.bella.BellaBoutique.model.products.Product;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", initialValue = 1000, allocationSize = 1)
    private long orderId;

    private String orderNumber;
    private String orderDate;
    private String orderStatus;
    private String paymentMethod;
    private double totalPrice;
    private String name;
    private String address;
    private String postalCode;
    private String city;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
}

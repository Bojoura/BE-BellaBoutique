package com.bella.BellaBoutique.model.products;

import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.model.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String category;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(precision = 10, scale = 2)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal stock;

    @Column(length = 100)
    private String brand;

    @Column(length = 50)
    private String sku;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(precision = 5, scale = 2)
    private BigDecimal width;

    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @Column(precision = 5, scale = 2)
    private BigDecimal depth;

    @Column(length = 255)
    private String warrantyInformation;

    @Column(length = 255)
    private String shippingInformation;

    @Column(length = 50)
    private String availabilityStatus;

    @Column(length = 255)
    private String returnPolicy;

    private Integer minimumOrderQuantity;

    @Column(updatable = false)
    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Column(length = 50)
    private String barcode;

    @Column(length = 255)
    private String qrCode;

    @Column(length = 255)
    private String thumbnail;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tags")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "images_url")
    private List<String> images;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToOne
    private User user;
}
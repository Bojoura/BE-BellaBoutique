package com.bella.BellaBoutique.model.inventory;

import com.bella.BellaBoutique.model.products.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private String location;

    private String status;

    @Column(name = "minimum_threshold")
    private Integer minimumThreshold;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
} 
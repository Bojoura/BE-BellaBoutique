package com.bella.BellaBoutique.model.reviews;

import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.model.users.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerName;

    @Column(length = 500)
    private String comment;

    private int rating;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reviewDate;

    @ManyToOne
    private Product product;

    @Column
    private String reviewerEmail;

    @ManyToOne
    private User user;
}
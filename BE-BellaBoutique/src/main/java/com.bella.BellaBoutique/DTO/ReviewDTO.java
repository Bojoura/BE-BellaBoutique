package com.bella.BellaBoutique.DTO;

import com.bella.BellaBoutique.model.products.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Product product;
    private Long productId;
    private String reviewerName;
    private String comment;
    private int rating;
    private LocalDateTime reviewDate;
}


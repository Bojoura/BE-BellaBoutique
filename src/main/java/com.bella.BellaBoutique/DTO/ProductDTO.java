package com.bella.BellaBoutique.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private BigDecimal discountPercentage;
    private BigDecimal rating;
    private BigDecimal stock;
    private String brand;
    private String sku;
    private BigDecimal weight;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal depth;
    private String warrantyInformation;
    private String shippingInformation;
    private String availabilityStatus;
    private String returnPolicy;
    private String barcode;
    private String qrCode;
    private String thumbnail;
    private List<String> tags;
    private List<String> images;
    private List<ReviewDTO> reviews;

}


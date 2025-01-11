package com.bella.BellaBoutique.DTO;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Category cannot be blank")
    @Size(min = 1, max = 255, message = "Category must be between 1 and 255 characters")
    private String category;

    @NotNull(message = "Price is required")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number with up to 10 digits and 2 decimal places")
    @Min(value = 0, message = "Price must be at least 0")
    private BigDecimal price;

    @Digits(integer = 3, fraction = 2, message = "Discount percentage must be a valid decimal number with up to 3 digits and 2 decimal places")
    @Min(value = 0, message = "Discount percentage cannot be negative")
    @Max(value = 100, message = "Discount percentage cannot exceed 100")
    private BigDecimal discountPercentage;

    @Digits(integer = 2, fraction = 1, message = "Rating must be a valid decimal number with up to 2 digits and 1 decimal place")
    @Min(value = 0, message = "Rating cannot be less than 0")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private BigDecimal rating;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotBlank(message = "Brand cannot be blank")
    @Size(min = 1, max = 255, message = "Brand must be between 1 and 255 characters")
    private String brand;

    @NotBlank(message = "SKU cannot be blank")
    @Size(min = 1, max = 255, message = "SKU must be between 1 and 255 characters")
    private String sku;

    @Digits(integer = 10, fraction = 3, message = "Weight must be a valid decimal number with up to 10 digits and 3 decimal places")
    @Min(value = 0, message = "Weight must be at least 0")
    private BigDecimal weight;

    @Digits(integer = 10, fraction = 3, message = "Width must be a valid decimal number with up to 10 digits and 3 decimal places")
    @Min(value = 0, message = "Width must be at least 0")
    private BigDecimal width;

    @Digits(integer = 10, fraction = 3, message = "Height must be a valid decimal number with up to 10 digits and 3 decimal places")
    @Min(value = 0, message = "Height must be at least 0")
    private BigDecimal height;

    @Digits(integer = 10, fraction = 3, message = "Depth must be a valid decimal number with up to 10 digits and 3 decimal places")
    @Min(value = 0, message = "Depth must be at least 0")
    private BigDecimal depth;

    @Size(max = 500, message = "Warranty information must not exceed 500 characters")
    private String warrantyInformation;

    @Size(max = 500, message = "Shipping information must not exceed 500 characters")
    private String shippingInformation;

    @NotBlank(message = "Availability status cannot be blank")
    @Size(min = 1, max = 255, message = "Availability status must be between 1 and 255 characters")
    private String availabilityStatus;

    @Size(max = 500, message = "Return policy must not exceed 500 characters")
    private String returnPolicy;

    @Min(value = 1, message = "Minimum order quantity must be at least 1")
    private Integer minimumOrderQuantity;

    @NotBlank(message = "Barcode cannot be blank")
    @Pattern(regexp = "^[0-9A-Za-z]+$", message = "Barcode must contain only alphanumeric characters")
    private String barcode;

    @Pattern(regexp = "^[0-9A-Za-z]+$", message = "QR Code must contain only alphanumeric characters")
    private String qrCode;

    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    private String thumbnail;

    @NotEmpty(message = "Images list cannot be empty")
    private List<@Size(max = 500, message = "Each image URL must not exceed 500 characters") String> images;

    @NotEmpty(message = "Tags list cannot be empty")
    private List<@Size(max = 50, message = "Each tag must not exceed 50 characters") String> tags;

    private List<ReviewDTO> reviews;
}

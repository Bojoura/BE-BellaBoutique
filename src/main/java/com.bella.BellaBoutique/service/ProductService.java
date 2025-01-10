package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.ProductDTO;
import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .discountPercentage(product.getDiscountPercentage())
                .rating(product.getRating())
                .stock(product.getStock())
                .brand(product.getBrand())
                .sku(product.getSku())
                .weight(product.getWeight())
                .width(product.getWidth())
                .height(product.getHeight())
                .depth(product.getDepth())
                .warrantyInformation(product.getWarrantyInformation())
                .shippingInformation(product.getShippingInformation())
                .availabilityStatus(product.getAvailabilityStatus())
                .returnPolicy(product.getReturnPolicy())
                .minimumOrderQuantity(product.getMinimumOrderQuantity())
                .barcode(product.getBarcode())
                .qrCode(product.getQrCode())
                .thumbnail(product.getThumbnail())
                .images(product.getImages())
                .tags(product.getTags())
                .reviews(product.getReviews() != null ?
                product.getReviews().stream()
                        .map(this::convertToReviewDTO)
                        .collect(Collectors.toList()) :
                Collections.emptyList())
                .build();
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .reviewerName(review.getReviewerName())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewDate(review.getReviewDate())
                .reviewerEmail(review.getReviewerEmail())
                .build();
    }

    public Product createProduct(ProductDTO productDTO) {
        if (productDTO.getTitle() == null || productDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }

        Product product = new Product();
        updateProductFromDTO(product, productDTO);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (productDTO.getTitle() == null || productDTO.getTitle().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be null or empty");
            }

            updateProductFromDTO(product, productDTO);

            return productRepository.save(product);
        }
        return null;
    }

    private void updateProductFromDTO(Product product, ProductDTO dto) {
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        if (dto.getCategory() != null) {
            if (!isValidCategory(dto.getCategory())) {
                throw new IllegalArgumentException("Ongeldige categorie: " + dto.getCategory());
            }
            product.setCategory(dto.getCategory());
        }
        product.setPrice(dto.getPrice());
        product.setDiscountPercentage(dto.getDiscountPercentage());
        product.setRating(dto.getRating());
        product.setStock(dto.getStock());
        product.setBrand(dto.getBrand());
        product.setSku(dto.getSku());
        product.setWeight(dto.getWeight());
        product.setWidth(dto.getWidth());
        product.setHeight(dto.getHeight());
        product.setDepth(dto.getDepth());
        product.setWarrantyInformation(dto.getWarrantyInformation());
        product.setShippingInformation(dto.getShippingInformation());
        product.setAvailabilityStatus(dto.getAvailabilityStatus());
        product.setReturnPolicy(dto.getReturnPolicy());
        product.setMinimumOrderQuantity(dto.getMinimumOrderQuantity());
        product.setBarcode(dto.getBarcode());
        product.setQrCode(dto.getQrCode());
        product.setThumbnail(dto.getThumbnail());
        product.setImages(dto.getImages());
        product.setTags(dto.getTags());
    }

    public boolean isValidCategory(String category) {
        if (category == null || category.isEmpty()) {
            return false;
        }

        List<String> validCategories = Arrays.asList("beauty", "fashion", "home");

        return validCategories.contains(category.toLowerCase());
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(4.0, 10);
    }
}


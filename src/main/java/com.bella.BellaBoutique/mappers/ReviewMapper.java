package com.bella.BellaBoutique.mappers;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.repository.ProductRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Component
public class ReviewMapper {

    @Autowired
    private ProductRepository productRepository;

    public ReviewDTO toDto(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewerName(review.getReviewerName())
                .reviewerEmail(review.getReviewerEmail())
                .reviewDate(review.getReviewDate())
                .build();
    }

    public Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setReviewerName(dto.getReviewerName());
        review.setReviewerEmail(dto.getReviewerEmail());
        review.setReviewDate(dto.getReviewDate());
        
        // Haal Product op basis van productId
        if (dto.getProductId() != null) {
            productRepository.findById(dto.getProductId())
                .ifPresent(review::setProduct);
        }
        
        return review;
    }

    public List<ReviewDTO> toDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}




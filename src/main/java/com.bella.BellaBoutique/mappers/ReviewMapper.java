package com.bella.BellaBoutique.mappers;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.model.reviews.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setProductId(review.getProduct().getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setReviewDate(review.getReviewDate());
        return dto;
    }

    public Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setProduct(dto.getProduct());
        review.setReviewerName(dto.getReviewerName());
        review.setReviewDate(dto.getReviewDate());
        return review;
    }

    public List<ReviewDTO> toDtoList(List<Review> reviews) {
        return reviews.stream().map(this::toDto).collect(Collectors.toList());
    }
}




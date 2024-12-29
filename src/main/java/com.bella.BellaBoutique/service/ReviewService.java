package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.mappers.ReviewMapper;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviewMapper.toDtoList(reviews);
    }

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    public Optional<ReviewDTO> updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setComment(reviewDTO.getComment());
            review.setRating(reviewDTO.getRating());
            Review updatedReview = reviewRepository.save(review);
            return Optional.of(reviewMapper.toDto(updatedReview));
        }
        return Optional.empty();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}






package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.mappers.ReviewMapper;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReviewsByProductId_shouldReturnReviews() {
        // Arrange
        Long productId = 1L;
        List<Review> reviews = List.of(new Review());
        List<ReviewDTO> reviewDTOs = List.of(new ReviewDTO());
        when(reviewRepository.findByProductId(productId)).thenReturn(reviews);
        when(reviewMapper.toDtoList(reviews)).thenReturn(reviewDTOs);

        // Act
        List<ReviewDTO> result = reviewService.getReviewsByProductId(productId);

        // Assert
        assertEquals(reviewDTOs, result);
        verify(reviewRepository, times(1)).findByProductId(productId);
        verify(reviewMapper, times(1)).toDtoList(reviews);
    }

    @Test
    void addReview_shouldSaveAndReturnReview() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        Review review = new Review();
        Review savedReview = new Review();
        ReviewDTO savedReviewDTO = new ReviewDTO();
        when(reviewMapper.toEntity(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(savedReview);
        when(reviewMapper.toDto(savedReview)).thenReturn(savedReviewDTO);

        // Act
        ReviewDTO result = reviewService.addReview(reviewDTO);

        // Assert
        assertEquals(savedReviewDTO, result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void updateReview_shouldUpdateAndReturnReview() {
        // Arrange
        Long id = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setComment("Updated comment");
        reviewDTO.setRating(5);

        Review existingReview = new Review();
        existingReview.setId(id);
        Review updatedReview = new Review();
        updatedReview.setComment("Updated comment");
        updatedReview.setRating(5);

        ReviewDTO updatedReviewDTO = new ReviewDTO();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(existingReview)).thenReturn(updatedReview);
        when(reviewMapper.toDto(updatedReview)).thenReturn(updatedReviewDTO);

        // Act
        Optional<ReviewDTO> result = reviewService.updateReview(id, reviewDTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedReviewDTO, result.get());
        verify(reviewRepository, times(1)).findById(id);
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    void updateReview_shouldReturnEmptyIfReviewDoesNotExist() {
        // Arrange
        Long id = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<ReviewDTO> result = reviewService.updateReview(id, reviewDTO);

        // Assert
        assertFalse(result.isPresent());
        verify(reviewRepository, times(1)).findById(id);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_shouldCallRepository() {
        // Arrange
        Long id = 1L;

        // Act
        reviewService.deleteReview(id);

        // Assert
        verify(reviewRepository, times(1)).deleteById(id);
    }
}

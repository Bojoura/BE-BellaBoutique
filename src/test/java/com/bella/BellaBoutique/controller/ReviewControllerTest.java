package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReviewsByProductId_shouldReturnReviews() {
        // Arrange
        Long productId = 1L;
        List<ReviewDTO> reviews = List.of(new ReviewDTO());
        when(reviewService.getReviewsByProductId(productId)).thenReturn(reviews);

        // Act
        ResponseEntity<List<ReviewDTO>> response = reviewController.getReviewsByProductId(productId);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
        verify(reviewService, times(1)).getReviewsByProductId(productId);
    }

    @Test
    void getReviewsByProductId_shouldReturnNoContentIfEmpty() {
        // Arrange
        Long productId = 1L;
        when(reviewService.getReviewsByProductId(productId)).thenReturn(List.of());

        // Act
        ResponseEntity<List<ReviewDTO>> response = reviewController.getReviewsByProductId(productId);

        // Assert
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(reviewService, times(1)).getReviewsByProductId(productId);
    }

    @Test
    void addReview_shouldReturnCreatedReview() {
        // Arrange
        Long productId = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        ReviewDTO createdReview = new ReviewDTO();
        when(reviewService.addReview(reviewDTO)).thenReturn(createdReview);

        // Act
        ResponseEntity<ReviewDTO> response = reviewController.addReview(productId, reviewDTO);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(createdReview, response.getBody());
        verify(reviewService, times(1)).addReview(reviewDTO);
    }

    @Test
    void updateReview_shouldReturnUpdatedReview() {
        // Arrange
        Long id = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        ReviewDTO updatedReview = new ReviewDTO();
        when(reviewService.updateReview(id, reviewDTO)).thenReturn(Optional.of(updatedReview));

        // Act
        ResponseEntity<ReviewDTO> response = reviewController.updateReview(id, reviewDTO);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedReview, response.getBody());
        verify(reviewService, times(1)).updateReview(id, reviewDTO);
    }

    @Test
    void updateReview_shouldReturnNotFoundIfNotExists() {
        // Arrange
        Long id = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        when(reviewService.updateReview(id, reviewDTO)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ReviewDTO> response = reviewController.updateReview(id, reviewDTO);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(reviewService, times(1)).updateReview(id, reviewDTO);
    }

    @Test
    void deleteReview_shouldReturnNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = reviewController.deleteReview(id);

        // Assert
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).deleteReview(id);
    }
}

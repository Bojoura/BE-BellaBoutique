package com.bella.BellaBoutique.integrationtest;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.controller.ReviewController;
import com.bella.BellaBoutique.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private ReviewDTO review1;
    private ReviewDTO review2;

    @BeforeEach
    void setUp() {
        review1 = ReviewDTO.builder()
                .id(1L)
                .productId(1L)
                .reviewerName("John Doe")
                .comment("Great product!")
                .rating(5)
                .reviewDate(LocalDateTime.now())
                .reviewerEmail("john.doe@example.com")
                .build();

        review2 = ReviewDTO.builder()
                .id(2L)
                .productId(1L)
                .reviewerName("Jane Smith")
                .comment("Good value for money.")
                .rating(4)
                .reviewDate(LocalDateTime.now())
                .reviewerEmail("jane.smith@example.com")
                .build();
    }

    @Test
    void getReviewsByProductId() throws Exception {
        given(reviewService.getReviewsByProductId(1L)).willReturn(List.of(review1, review2));

        mockMvc.perform(get("/products/1/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Great product!"))
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].reviewerName").value("John Doe"))
                .andExpect(jsonPath("$[0].reviewerEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].rating").value(4))
                .andExpect(jsonPath("$[1].comment").value("Good value for money."))
                .andExpect(jsonPath("$[1].productId").value(1))
                .andExpect(jsonPath("$[1].reviewerName").value("Jane Smith"))
                .andExpect(jsonPath("$[1].reviewerEmail").value("jane.smith@example.com"));
    }

    @Test
    void addReview() throws Exception {
        given(reviewService.addReview(review1)).willReturn(review1);

        mockMvc.perform(post("/products/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(review1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("rating").value(5))
                .andExpect(jsonPath("comment").value("Great product!"))
                .andExpect(jsonPath("productId").value(1))
                .andExpect(jsonPath("reviewerName").value("John Doe"))
                .andExpect(jsonPath("reviewerEmail").value("john.doe@example.com"));
    }

    @Test
    void updateReview() throws Exception {
        ReviewDTO updatedReview = ReviewDTO.builder()
                .id(1L)
                .productId(1L)
                .reviewerName("John Doe")
                .comment("Updated comment.")
                .rating(4)
                .reviewDate(LocalDateTime.now())
                .reviewerEmail("john.doe@example.com")
                .build();

        given(reviewService.updateReview(1L, updatedReview)).willReturn(Optional.of(updatedReview));

        mockMvc.perform(put("/products/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedReview)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("rating").value(4))
                .andExpect(jsonPath("comment").value("Updated comment."))
                .andExpect(jsonPath("productId").value(1))
                .andExpect(jsonPath("reviewerName").value("John Doe"))
                .andExpect(jsonPath("reviewerEmail").value("john.doe@example.com"));
    }

    @Test
    void deleteReview() throws Exception {
        mockMvc.perform(delete("/products/reviews/1"))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

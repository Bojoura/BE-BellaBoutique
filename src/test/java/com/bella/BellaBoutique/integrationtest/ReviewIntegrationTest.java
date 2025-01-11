package com.bella.BellaBoutique.integrationtest;

import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
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
        mockMvc.perform(get("/products/1001/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Geweldig product!"))
                .andExpect(jsonPath("$[0].productId").value(1001))
                .andExpect(jsonPath("$[0].reviewerName").value("Test User"))
                .andExpect(jsonPath("$[0].reviewerEmail").value("test@test.com"));
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

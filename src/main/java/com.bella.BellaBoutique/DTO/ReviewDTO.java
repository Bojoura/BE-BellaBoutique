package com.bella.BellaBoutique.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Reviewer name cannot be blank")
    @Size(min = 1, max = 100, message = "Reviewer name must be between 1 and 100 characters")
    private String reviewerName;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    private String comment;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotNull(message = "Review date is required")
    @PastOrPresent(message = "Review date cannot be in the future")
    private LocalDateTime reviewDate;

    @NotBlank(message = "Reviewer email cannot be blank")
    @Email(message = "Reviewer email must be a valid email address")
    private String reviewerEmail;
}




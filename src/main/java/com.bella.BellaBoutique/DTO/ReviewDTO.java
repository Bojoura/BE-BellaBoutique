package com.bella.BellaBoutique.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long productId;
    private String reviewerName;
    private String comment;
    private int rating;
    private LocalDateTime reviewDate;
    private String reviewerEmail;
}


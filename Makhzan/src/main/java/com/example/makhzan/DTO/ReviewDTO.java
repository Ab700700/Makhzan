package com.example.makhzan.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private Integer storageid;
    @NotNull(message = "rating cannot be null")
    @Positive(message = "enter correct rating")
    @Max(value = 5,message = "Rating should not be above 5")
    private Integer rating;
    @NotNull(message = "comment cannot be null")
    private String comment;

}

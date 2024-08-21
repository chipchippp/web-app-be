package com.example.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
@AllArgsConstructor
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;

    @Size(min = 5, max = 300, message = "Image URL must be less than 300 characters")
    @JsonProperty("img_url")
    private String imgUrl;
}

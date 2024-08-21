package com.example.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Orders id is required")
    private Long orderId;
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id is required")
    private Long productId;

    @Min(value = 0, message = "Price must be >= 0")
    private Double price;

    @Min(value = 1, message = "Number of product must be >= 1")
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @JsonProperty("total_price")
    @Min(value = 0, message = "Total price must be >= 0")
    private Double totalPrice;

    private String color;
}

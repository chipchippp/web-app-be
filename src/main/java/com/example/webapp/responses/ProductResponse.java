package com.example.webapp.responses;

import com.example.webapp.models.Products;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Double price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponse fromProduct(Products products) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .thumbnail(products.getThumbnail())
                .description(products.getDescription())
                .categoryId(products.getCategoryId().getId())
                .build();
        productResponse.setCreatedAt(products.getCreatedAt());
        productResponse.setUpdatedAt(products.getUpdatedAt());
        return productResponse;
    }
}

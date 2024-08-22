package com.example.webapp.responses;

import com.example.webapp.models.OrderDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long order;

    @JsonProperty("product_id")
    private Long product;

    @JsonProperty()
    private Double price;
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @JsonProperty("total_price")
    private Double totalPrice;
    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetails orderDetail) {
           return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .order(orderDetail.getOrder().getId())
                .product(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProduct(orderDetail.getNumberOfProducts())
                .totalPrice(orderDetail.getTotalPrice())
                .color(orderDetail.getColor())
                .build();
    }
}

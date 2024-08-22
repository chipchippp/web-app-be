package com.example.webapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(nullable = false)
    private Double price;
    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
    private String color;
}

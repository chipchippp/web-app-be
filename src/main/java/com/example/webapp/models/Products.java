package com.example.webapp.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(length = 300)
    private String thumbnail;
    @Column(length = 300)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
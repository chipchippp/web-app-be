package com.example.webapp.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {
    public static final int MAXIMUM_IMAGE_PER_PRODUCT =5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(name = "img_url", length = 300)
    private String imgUrl;
}

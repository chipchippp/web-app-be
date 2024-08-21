package com.example.webapp.service.impl;


import com.example.webapp.dto.ProductDTO;
import com.example.webapp.dto.ProductImageDTO;
import com.example.webapp.models.ProductImage;
import com.example.webapp.models.Products;
import com.example.webapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Page<ProductResponse> getAllProduct(PageRequest pageRequest) throws Exception;
    Products getProductById(Long id) throws Exception;
    public Products saveProduct(ProductDTO productDTO) throws Exception;
    Products updateProduct(Long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO
    ) throws Exception;
}

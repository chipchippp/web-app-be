package com.example.webapp.service;

import com.example.webapp.dto.ProductDTO;
import com.example.webapp.dto.ProductImageDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.exceptions.InvalidParamException;
import com.example.webapp.models.Category;
import com.example.webapp.models.ProductImage;
import com.example.webapp.models.Products;
import com.example.webapp.repository.CategoryRepository;
import com.example.webapp.repository.ProductImageRepository;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.responses.ProductResponse;
import com.example.webapp.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }

    @Override
    public Products getProductById(Long id) throws DataNotFound {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFound("Product not found with id: " + id));
    }

    @Override
    public Products saveProduct(ProductDTO productDTO) throws DataNotFound {
        try {
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFound("Category not found with id: " + productDTO.getCategoryId()));
            Products newProduct = Products.builder()
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .thumbnail(productDTO.getThumbnail())
                    .description(productDTO.getDescription())
                    .categoryId(existingCategory)
                    .build();
            return productRepository.save(newProduct);
        } catch (DataNotFound e) {
            throw new DataNotFound("Category not found");
        }
    }

    @Override
    public Products updateProduct(Long id, ProductDTO productDTO) throws DataNotFound {
        try {
            Products existingProduct = getProductById(id);
            if (existingProduct != null) {
                Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() ->
                                new DataNotFound("Category not found with id: " + productDTO.getCategoryId()));
                existingProduct.setName(productDTO.getName());
                existingProduct.setPrice(productDTO.getPrice());
                existingProduct.setThumbnail(productDTO.getThumbnail());
                existingProduct.setDescription(productDTO.getDescription());
                existingProduct.setCategoryId(existingCategory);
                return productRepository.save(existingProduct);
            }
        } catch (DataNotFound e) {
            throw new DataNotFound("Category not found");
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Products> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO
    ) throws Exception {
        Products existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new DataNotFound("Product not found with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imgUrl(productImageDTO.getImgUrl())
                .build();
//        kh cho insert quá 5 ảnh cho 1 product
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGE_PER_PRODUCT) {
            throw new InvalidParamException("Product image size must be =< "
                    + ProductImage.MAXIMUM_IMAGE_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}

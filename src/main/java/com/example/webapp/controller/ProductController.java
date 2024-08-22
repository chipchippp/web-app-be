package com.example.webapp.controller;


import com.example.webapp.dto.ProductDTO;
import com.example.webapp.dto.ProductImageDTO;
import com.example.webapp.models.ProductImage;
import com.example.webapp.models.Products;
import com.example.webapp.responses.ProductListResponse;
import com.example.webapp.responses.ProductResponse;
import com.example.webapp.service.impl.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProduct(pageRequest);
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> productList = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(productList)
                .totalPage(totalPage)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable Long id
    ) {
        try {
            Products product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        try{
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            Products newProduct = productService.saveProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Product added " + productDTO);
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws Exception {
        try {
            Products existingProduct = productService.getProductById(productId);
            List<ProductImage> productImages = new ArrayList<>();
            if (files.size() > ProductImage.MAXIMUM_IMAGE_PER_PRODUCT){
                return ResponseEntity.badRequest()
                        .body("Max file upload is 5 files");
            }
            files = files == null ? new ArrayList<>() : files;
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size too large!, max size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Unsupported media type, only accept image");
                }
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imgUrl(fileName)
                                .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImage(file) || file.getOriginalFilename() != null) {
            throw new IOException("Unsupported media type, only accept image");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Products updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Product deleted id: " + id + " successfully");
    }
}

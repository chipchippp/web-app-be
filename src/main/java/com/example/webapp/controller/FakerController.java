package com.example.webapp.controller;

import com.example.webapp.dto.CategoryDTO;
import com.example.webapp.dto.ProductDTO;
import com.example.webapp.dto.UserDTO;
import com.example.webapp.service.impl.CategoryService;
import com.example.webapp.service.impl.ProductService;
import com.example.webapp.service.impl.UserService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/faker")
public class FakerController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

//    @PostMapping("/generateUser")
//    public ResponseEntity<String> generateFakerUser() {
//        Faker faker = new Faker();
//        for (int i = 0; i < 10; i++) {
//            String username = faker.name().fullName();
//            if (userService.existsByName(username)) {
//                continue;
//            }
//            UserDTO userDTO = UserDTO.builder()
//                    .fullName(username)
//                    .password(faker.internet().password())
//                    .email(faker.internet().emailAddress())
//                    .build();
//            try {
//                userService.saveUser(userDTO);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Faker user successfully");
//    }

//    @PostMapping("/generateCategory")
    public ResponseEntity<String> generateFakerCategory() {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            String categoryName = faker.commerce().department();
            if (categoryService.existsByName(categoryName)) {
                continue;
            }
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .name(categoryName)
                    .build();
             try {
                 categoryService.saveCategory(categoryDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Faker category successfully");
    }

//    @PostMapping("/generateProduct")
    public ResponseEntity<String> generateFakerProduct() throws Exception {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price(faker.number().randomDouble(2, 1, 200))
                    .thumbnail(faker.internet().image())
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(1, 4))
                    .build();
            try {
                productService.saveProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Faker product successfully");
    }
}

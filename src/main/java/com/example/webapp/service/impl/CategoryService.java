package com.example.webapp.service.impl;


import com.example.webapp.dto.CategoryDTO;
import com.example.webapp.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Long id);
    Category saveCategory(CategoryDTO category);
    Category updateCategory(Long id, CategoryDTO category);
    void deleteCategory(Long id);
    boolean existsByName(String name);
}

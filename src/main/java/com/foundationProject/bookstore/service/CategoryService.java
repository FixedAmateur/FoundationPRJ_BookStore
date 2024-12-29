package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.dto.CategoryDto;
import com.foundationProject.bookstore.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse addCategory(CategoryDto categoryDto);

    CategoryResponse updateCategory(Long categoryId, CategoryDto categoryDto);

    CategoryResponse getCategoryById(Long categoryId);

    List<CategoryResponse> getAllCategory();

    String deleteCategory(Long categoryId);

    CategoryResponse getCategoryByCategoryName(String categoryName);
}

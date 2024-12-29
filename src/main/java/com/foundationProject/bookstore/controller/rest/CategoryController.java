package com.foundationProject.bookstore.controller.rest;


import com.foundationProject.bookstore.model.response.ApiResponse;
import com.foundationProject.bookstore.service.CategoryService;
import com.foundationProject.bookstore.model.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Add Category", description = "Add Category API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = ApiResponse.success(categoryService.addCategory(categoryDto));
        apiResponse.setCode(201);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Category", description = "Get Category API")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryById(categoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get All Category", description = "Get All Category API")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategory() {
        ApiResponse apiResponse = ApiResponse.success(categoryService.getAllCategory());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update Category", description = "Update Category API")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = ApiResponse.success(categoryService.updateCategory(categoryId, categoryDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete Category", description = "Delete Category API")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        ApiResponse apiResponse = ApiResponse.success(categoryService.deleteCategory(categoryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Category By Category Name", description = "Get Category By Category Name API")
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable String categoryName) {
        ApiResponse apiResponse = ApiResponse.success(categoryService.getCategoryByCategoryName(categoryName));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
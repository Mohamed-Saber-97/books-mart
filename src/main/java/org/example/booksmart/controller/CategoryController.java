package org.example.booksmart.controller;

import org.example.booksmart.converter.CategoryToCategoryDtoConverter;
import org.example.booksmart.dto.CategoryDto;
import org.example.booksmart.model.Category;
import org.example.booksmart.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody Category category) {
        CategoryDto updatedCategory = categoryService.update(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.save(category);
        CategoryDto categoryDto = CategoryToCategoryDtoConverter.convert(newCategory);
        return ResponseEntity.ok(categoryDto);
    }
}

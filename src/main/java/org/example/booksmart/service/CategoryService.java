package org.example.booksmart.service;

import org.example.booksmart.converter.CategoryToCategoryDtoConverter;
import org.example.booksmart.dto.CategoryDto;
import org.example.booksmart.model.Category;
import org.example.booksmart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryToCategoryDtoConverter::convert).toList();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return CategoryToCategoryDtoConverter.convert(category);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public CategoryDto update(Category category) {
        Category updatedCategory = categoryRepository.save(category);
        return CategoryToCategoryDtoConverter.convert(updatedCategory);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setIsDeleted(true);
            categoryRepository.save(category);
        }
    }
}

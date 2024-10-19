package org.example.booksmart.converter;

import org.example.booksmart.dto.CategoryDto;
import org.example.booksmart.model.Category;

public class CategoryToCategoryDtoConverter {
    public static CategoryDto convert(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }
}

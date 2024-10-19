package org.example.booksmart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDto(
        Long id,
        String name,
        String author,
        String isbn,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDate releaseDate,
        String imagePath,
        Long categoryId
) {
}

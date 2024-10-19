package org.example.booksmart.converter;

import org.example.booksmart.dto.ProductDto;
import org.example.booksmart.model.Product;

public class ProductToProductDtoConverter {
    public static ProductDto convert(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getAuthor(),
                product.getIsbn(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getReleaseDate(),
                product.getImagePath(),
                product.getCategory().getId()
        );
    }
}

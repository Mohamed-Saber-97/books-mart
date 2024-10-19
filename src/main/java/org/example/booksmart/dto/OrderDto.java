package org.example.booksmart.dto;

public record OrderDto(
        Long id,
        Long buyerId,
        String status
) {

}

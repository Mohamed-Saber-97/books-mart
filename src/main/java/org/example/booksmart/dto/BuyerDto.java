package org.example.booksmart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BuyerDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String job,
        LocalDate birthDay,
        BigDecimal creditLimit,
        String country,
        String city,
        String street,
        String zipCode
) {
}

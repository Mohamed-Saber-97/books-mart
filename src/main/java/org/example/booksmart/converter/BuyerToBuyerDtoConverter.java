package org.example.booksmart.converter;

import org.example.booksmart.dto.BuyerDto;
import org.example.booksmart.model.Buyer;

public class BuyerToBuyerDtoConverter {
    public static BuyerDto convert(Buyer buyer) {
        return new BuyerDto(
                buyer.getId(),
                buyer.getAccount().getName(),
                buyer.getAccount().getEmail(),
                buyer.getAccount().getPhoneNumber(),
                buyer.getAccount().getJob(),
                buyer.getAccount().getBirthday(),
                buyer.getCreditLimit(),
                buyer.getAccount().getAddress().getCountry(),
                buyer.getAccount().getAddress().getCity(),
                buyer.getAccount().getAddress().getStreet(),
                buyer.getAccount().getAddress().getZipcode()
        );
    }
}

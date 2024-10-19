package org.example.booksmart.converter;

import org.example.booksmart.dto.OrderDto;
import org.example.booksmart.model.Order;

public class OrderToOrderDtoConverter {
    public static OrderDto convert(Order order) {
        return new OrderDto(
                order.getId(),
                order.getBuyer().getId(),
                order.getStatus().name()
        );
    }
}

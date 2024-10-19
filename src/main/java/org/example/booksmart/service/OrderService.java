package org.example.booksmart.service;

import org.example.booksmart.model.Order;
import org.example.booksmart.model.OrderProduct;
import org.example.booksmart.model.Product;
import org.example.booksmart.repository.OrderProductRepository;
import org.example.booksmart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderProduct> findProductsByBuyerId(Long buyerId, Long orderId) {
        return orderProductRepository.findProductsByBuyerIdAndOrderId(buyerId, orderId);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }

}

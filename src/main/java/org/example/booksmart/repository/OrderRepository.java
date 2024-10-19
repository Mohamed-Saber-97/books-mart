package org.example.booksmart.repository;


import org.example.booksmart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository< Order, Long > {

}

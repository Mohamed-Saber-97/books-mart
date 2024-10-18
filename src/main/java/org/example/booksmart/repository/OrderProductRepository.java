package org.example.booksmart.repository;

import org.example.booksmart.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository< OrderProduct, Integer > {
    
    @Query("SELECT op FROM OrderProduct op WHERE op.order.buyer.id = :buyerId AND op.order.id = :orderId AND op.isDeleted = false")
    List< OrderProduct > findProductsByBuyerIdAndOrderId(
            @Param("buyerId") Long buyerId,
            @Param("orderId") Long orderId
                                                        );
}

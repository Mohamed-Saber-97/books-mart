package org.example.booksmart.repository;

import jakarta.transaction.Transactional;
import org.example.booksmart.model.Buyer;
import org.example.booksmart.model.Order;
import org.example.booksmart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public interface BuyerRepository extends JpaRepository< Buyer, Long > {
    
    public default List< Order > findOrdersByBuyerId(Long buyerId) {
        Buyer buyer = findById(buyerId).orElse(null);
        if ( buyer != null ) {
            return new ArrayList<>(buyer.getOrders());
        }
        return Collections.emptyList();
    }
    
    public boolean existsByAccount_Email(String account_email);
    
    public boolean existsByAccount_PhoneNumber(String account_PhoneNumber);
    
    public Buyer findByAccount_PhoneNumber(String account_PhoneNumber);
    
    public Buyer findByAccount_Email(String account_email);
    
    @Query(
            "SELECT p FROM Product p " +
                    "JOIN Category c ON p MEMBER OF c.products " +
                    "JOIN Buyer b ON c MEMBER OF b.interests " +
                    "WHERE b.id = :buyerId " +
                    "AND p.isDeleted = false " +
                    "AND c.isDeleted = false"
    )
    List< Product > findInterestsByBuyerId(@Param("buyerId") Long buyerId);
    
    @Modifying
    @Transactional
    public default void addProductToCart(
            Buyer buyer,
            Product product,
            int quantity
                                        ) {
        buyer.addCartItem(product, quantity);
        
    }
    
    @Modifying
    @Transactional
    public default void removeProductFromCart(
            Buyer buyer,
            Product product
                                             ) {
        buyer.removeFromCart(product);
    }
    
    @Modifying
    @Transactional
    public default int incrementProductQuantity(
            Buyer buyer,
            Product product
                                               ) {
        int qty = buyer.addToCart(product, 1);
        return qty;
    }
    
    @Modifying
    @Transactional
    public default int decrementProductQuantity(
            Buyer buyer,
            Product product
                                               ) {
        
        int currentQuantity = buyer.getCart()
                                   .getOrDefault(product, 0);
        int qty = 1;
        if ( currentQuantity > 1 ) {
            qty = buyer.addToCart(product, -1);
        }
        return qty;
    }
    
    @Modifying
    @Transactional
    public default void clearCart(Buyer buyer) {
        buyer.clearCart();
    }
    
    @Modifying
    @Transactional
    public default void setProductQuantity(
            Buyer buyer,
            Product product,
            int quantity
                                          ) {
        if ( quantity > 0 ) {
            buyer.getCart()
                 .put(product, quantity);
        } else {
            buyer.getCart()
                 .remove(product);
        }
        save(buyer);
    }
    
    @Modifying
    @Transactional
    public default void addToWishlist(
            Buyer buyer,
            Product product
                                     ) {
        buyer.addToWishlist(product);
    }
    
    @Modifying
    @Transactional
    public default Buyer removeFromWishlist(
            Buyer buyer,
            Product product
                                           ) {
        buyer.removeFromWishlist(product);
        return buyer;
    }
    
    @Query("SELECT b FROM Buyer b WHERE b.isDeleted = false")
    Page< Buyer > findBuyersByIsDeletedFalse(PageRequest pageRequest);
    
    @Query("SELECT o FROM Order o WHERE o.buyer.id = :buyerId AND o.isDeleted = false")
    Page< Order > findOrdersByBuyerIdAndIsDeletedFalse(
            Long buyerId,
            PageRequest pageRequest
                                                      );
}

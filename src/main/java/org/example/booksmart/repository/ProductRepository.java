package org.example.booksmart.repository;


import org.example.booksmart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(
            Specification<Product> spec,
            Pageable pageable
    );


    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.quantity > 0")
    List<Product> findAllAvailable();

    Product findByIdAndIsDeletedIsFalse(Long id);

    boolean existsByIsbn(String isbn);

    @Query("SELECT product FROM Product product where product.id in :ids AND product.isDeleted = false AND product.quantity > 0")
    List<Product> findByIds(Iterable<Long> ids);

    @Query("SELECT p, p.quantity FROM Product p WHERE p.id IN :productIds AND p.isDeleted = false AND p.quantity > 0")
    List<Object[]> findByIdsWithQuantities(@Param("productIds") Iterable<Long> ids);

}

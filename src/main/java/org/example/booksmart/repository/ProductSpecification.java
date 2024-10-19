package org.example.booksmart.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.example.booksmart.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Map;

public class ProductSpecification implements Specification< Product > {
    
    private final Map< String, String > queryParameters;
    
    public ProductSpecification(Map< String, String > queryParameters) {
        this.queryParameters = queryParameters;
    }
    
    @Override public Predicate toPredicate(
            @NotNull Root< Product > root,
            CriteriaQuery< ? > query,
            CriteriaBuilder cb
                                          ) {
        Predicate predicate = cb.conjunction(); // Start with an empty "true" predicate
        
        // Minimum price filter
        String minPrice = queryParameters.get("MINIMUM_PRICE");
        if ( minPrice != null ) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), new BigDecimal(minPrice)));
        }
        
        // Maximum price filter
        String maxPrice = queryParameters.get("MAXIMUM_PRICE");
        if ( maxPrice != null ) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), new BigDecimal(maxPrice)));
        }
        
        // Category filter
        String category = queryParameters.get("CATEGORY");
        if ( category != null ) {
            predicate = cb.and(predicate,
                               cb.equal(root.get("category")
                                            .get("id"), Long.parseLong(category)));
        }
        
        // Name filter
        String name = queryParameters.get("NAME");
        if ( name != null ) {
            predicate = cb.and(predicate, cb.like(root.get("name"), "%" + name + "%"));
        }
        
        // Is deleted filter
        predicate = cb.and(predicate, cb.equal(root.get("isDeleted"), false));
        
        return predicate;
    }
}

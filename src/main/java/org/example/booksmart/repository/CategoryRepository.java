package org.example.booksmart.repository;

import org.example.booksmart.model.Category;
import org.example.booksmart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository< Category, Long > {
    public boolean existsByName(String categoryName);
    
    public Category findByName(String categoryName);
    
}

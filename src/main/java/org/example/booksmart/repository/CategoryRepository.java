package org.example.booksmart.repository;


import org.example.booksmart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository< Category, Long > {
    public boolean existsByCategoryName(String categoryName);
    
    public boolean findByCategoryName(String categoryName);
    
}

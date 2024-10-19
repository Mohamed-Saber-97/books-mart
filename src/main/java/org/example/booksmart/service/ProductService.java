package org.example.booksmart.service;

import org.example.booksmart.model.Product;
import org.example.booksmart.repository.ProductRepository;
import org.example.booksmart.repository.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;




    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findFirstX(int x) {
        List<Product> products = productRepository.findAllAvailable();
        return products.subList(0, Math.min(products.size(), x));
    }

    public List<Product> search(Map<String, String> queryParameters, int pageNumber, int pageSize) {
        ProductSpecification productSpecification = new ProductSpecification(queryParameters);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (!queryParameters.isEmpty()) {
            return (productRepository.findAll(productSpecification, pageable)).getContent();
        }
        return (productRepository.findAll(productSpecification, pageable)).getContent();
    }

    public List<Product> findAllAvailable() {
        return productRepository.findAllAvailable();
    }

    public Product findAvailableProductById(Long id) {
        return productRepository.findByIdAndIsDeletedIsFalse(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public boolean existsByIsbn(String isbn) {
        return productRepository.existsByIsbn(isbn);
    }

    public List<Product> findByIds(Iterable<Long> ids) {
        return productRepository.findByIds(ids);
    }

    public Map<Product, Integer> findByIdsWithQuantities(Iterable<Long> ids) {
        List<Object[]> results =  productRepository.findByIdsWithQuantities(ids);

        Map<Product, Integer> productQuantityMap = new HashMap<>();
        for (Object[] result : results) {
            Product product = (Product) result[0];
            Integer quantity = (Integer) result[1];
            productQuantityMap.put(product, quantity);
        }
        return productQuantityMap;
    }
}
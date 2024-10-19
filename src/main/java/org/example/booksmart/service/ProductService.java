package org.example.booksmart.service;

import jakarta.transaction.Transactional;
import org.example.booksmart.converter.ProductToProductDtoConverter;
import org.example.booksmart.dto.ProductDto;
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

    public List<ProductDto> findAllAvailable() {
        List<Product> products = productRepository.findAllAvailable();
        return products.stream().map(ProductToProductDtoConverter::convert).toList();
    }

    public ProductDto findAvailableProductById(Long id) {
        Product product = productRepository.findByIdAndIsDeletedIsFalse(id);
        return ProductToProductDtoConverter.convert(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public ProductDto update(Product product) {
        Product updatedProduct = productRepository.save(product);
        System.out.println(updatedProduct);
        return ProductToProductDtoConverter.convert(updatedProduct);
    }

    public boolean existsByIsbn(String isbn) {
        return productRepository.existsByIsbn(isbn);
    }

    public List<Product> findByIds(Iterable<Long> ids) {
        return productRepository.findByIds(ids);
    }

    public Map<Product, Integer> findByIdsWithQuantities(Iterable<Long> ids) {
        List<Object[]> results = productRepository.findByIdsWithQuantities(ids);

        Map<Product, Integer> productQuantityMap = new HashMap<>();
        for (Object[] result : results) {
            Product product = (Product) result[0];
            Integer quantity = (Integer) result[1];
            productQuantityMap.put(product, quantity);
        }
        return productQuantityMap;
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setIsDeleted(true);
            productRepository.save(product);
        }
    }
}

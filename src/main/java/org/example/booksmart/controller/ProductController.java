package org.example.booksmart.controller;

import org.example.booksmart.converter.ProductToProductDtoConverter;
import org.example.booksmart.dto.ProductDto;
import org.example.booksmart.model.Product;
import org.example.booksmart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAllAvailable();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.findAvailableProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product) {
        System.out.println(product.getId());
        ProductDto updatedProduct = productService.update(product);
        System.out.println(updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        ProductDto productDto = ProductToProductDtoConverter.convert(newProduct);
        return ResponseEntity.ok(productDto);
    }
}

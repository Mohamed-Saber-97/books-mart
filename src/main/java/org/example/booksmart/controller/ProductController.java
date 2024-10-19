package org.example.booksmart.controller;

import org.example.booksmart.dto.ProductDto;
import org.example.booksmart.model.Product;
import org.example.booksmart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

//    @GetMapping
//    public String getProducts() {
//        return "Hello World";
//    }

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAllAvailable();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findAvailableProductById(id);
        return ResponseEntity.ok(product);
    }
//
//    // update product
//    @PatchMapping("/{id}")
//    public Response updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        Product updatedProduct = productService.updateProduct(id, product);
//        return Response.ok(updatedProduct).build();
//    }
//
//    // delete product
//    @DeleteMapping("/{id}")
//    public Response deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return Response.noContent().build();
//    }
//
//    // add product
//    @PostMapping
//    public Response addProduct(@RequestBody Product product) {
//        Product newProduct = productService.addProduct(product);
//        return Response.ok(newProduct).build();
//    }
}

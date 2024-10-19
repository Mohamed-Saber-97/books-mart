package org.example.booksmart.controller;

import org.apache.coyote.Response;
import org.example.booksmart.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping
    // get all products
    public Response getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return Response.ok(products).build();
    }

    @GetMapping("/{id}")
    // get product by id
    public Response getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Response.ok(product).build();
    }

    // update product
    @PatchMapping("/{id}")
    public Response updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return Response.ok(updatedProduct).build();
    }

    // delete product
    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }

    // add product
    @PostMapping
    public Response addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return Response.ok(newProduct).build();
    }
}

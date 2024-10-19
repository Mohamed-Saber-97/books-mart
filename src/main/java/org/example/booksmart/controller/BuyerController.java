package org.example.booksmart.controller;

import org.example.booksmart.converter.BuyerToBuyerDtoConverter;
import org.example.booksmart.converter.CategoryToCategoryDtoConverter;
import org.example.booksmart.dto.BuyerDto;
import org.example.booksmart.dto.CategoryDto;
import org.example.booksmart.model.Buyer;
import org.example.booksmart.model.Category;
import org.example.booksmart.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuyerController {
    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping
    public ResponseEntity<List<BuyerDto>> getAllCategories() {
        List<BuyerDto> buyers = buyerService.findAll();
        return ResponseEntity.ok(buyers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerDto> getCategoryById(@PathVariable Long id) {
        BuyerDto buyerDto = buyerService.findById(id);
        return ResponseEntity.ok(buyerDto);
    }

    @PatchMapping
    public ResponseEntity<BuyerDto> updateCategory(@RequestBody Buyer buyer) {
        BuyerDto updatedBuyer = buyerService.update(buyer);
        return ResponseEntity.ok(updatedBuyer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        buyerService.delete(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PostMapping
    public ResponseEntity<BuyerDto> addCategory(@RequestBody Buyer buyer) {
        Buyer newBuyer = buyerService.save(buyer);
        BuyerDto buyerDto = BuyerToBuyerDtoConverter.convert(newBuyer);
        return ResponseEntity.ok(buyerDto);
    }
}

package org.example.booksmart.controller;

import org.example.booksmart.converter.BuyerToBuyerDtoConverter;
import org.example.booksmart.dto.BuyerDto;
import org.example.booksmart.model.Buyer;
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
    public ResponseEntity<List<BuyerDto>> getAllBuyers() {
        List<BuyerDto> buyers = buyerService.findAll();
        return ResponseEntity.ok(buyers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerDto> getBuyerById(@PathVariable Long id) {
        BuyerDto buyerDto = buyerService.findById(id);
        return ResponseEntity.ok(buyerDto);
    }

    @PatchMapping
    public ResponseEntity<BuyerDto> updateBuyer(@RequestBody Buyer buyer) {
        BuyerDto updatedBuyer = buyerService.update(buyer);
        return ResponseEntity.ok(updatedBuyer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuyer(@PathVariable Long id) {
        buyerService.delete(id);
        return ResponseEntity.ok("Buyer deleted successfully");
    }

    @PostMapping
    public ResponseEntity<BuyerDto> addBuyer(@RequestBody Buyer buyer) {
        Buyer newBuyer = buyerService.save(buyer);
        BuyerDto buyerDto = BuyerToBuyerDtoConverter.convert(newBuyer);
        return ResponseEntity.ok(buyerDto);
    }
}

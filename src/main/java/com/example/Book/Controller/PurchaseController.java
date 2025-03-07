package com.example.Book.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Book.Entity.Purchase;
import com.example.Book.Service.PurchaseService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // Purchase a book
    @PostMapping("/buy")
    public Purchase purchaseBook(@RequestParam int userId, @RequestParam int bookId) {
        return purchaseService.purchaseBook(userId, bookId);
    }

    // Get logged-in user's purchase history
    @GetMapping("/history/{userId}")
    public List<Purchase> getPurchaseHistory(@PathVariable int userId) {
        return purchaseService.getPurchaseHistory(userId);
    }

    @GetMapping("/purchase-history")
    public ResponseEntity<Page<Map<String, Object>>> getAllUserHistory(Pageable pageable) {
        Page<Map<String, Object>> history = purchaseService.getAllUserHistory(pageable);
        return ResponseEntity.ok(history);
    }

}

package com.example.Book.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Book.Entity.Purchase;
import com.example.Book.Service.PurchaseService;

import java.time.LocalDateTime;
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
    @GetMapping("/purchase-history")
    public ResponseEntity<Page<Map<String, Object>>> getAllUserHistory(Pageable pageable) {
        Page<Map<String, Object>> history = purchaseService.getAllUserHistory(pageable);
        return ResponseEntity.ok(history);
    }

     @GetMapping("/search")
    public List<Purchase> getPurchasesByDateRange(
            @RequestParam String from,
            @RequestParam String to) {
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);
        return purchaseService.getPurchasesBetweenDates(fromDate, toDate);
    }
}

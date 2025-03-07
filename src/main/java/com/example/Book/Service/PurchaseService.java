package com.example.Book.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.example.Book.Entity.Book;
import com.example.Book.Entity.Purchase;
import com.example.Book.Entity.User;
import com.example.Book.Repo.PurchaseRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;


@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Transactional
    public Purchase purchaseBook(int userId, int bookId) {
        Optional<User> userOpt = userService.findById(userId);
        Optional<Book> bookOpt = bookService.findById(bookId);

        if (userOpt.isPresent() && bookOpt.isPresent()) {
            User user = userOpt.get();
            Book book = bookOpt.get();

            boolean alreadyPurchased = purchaseRepository.existsByUserAndBook(user, book);
            if (alreadyPurchased) {
                throw new RuntimeException("You have already purchased this book!");
            }

            bookService.updateBook(bookId, book);

            Purchase purchase = new Purchase(user, book, LocalDateTime.now());
            return purchaseRepository.save(purchase);
        }
        throw new RuntimeException("User or Book not found");
    }

    public List<Purchase> getPurchaseHistory(int userId) {
        return userService.findById(userId)
            .map(purchaseRepository::findByUser)
            .orElse(Collections.emptyList()); // Return empty list if user not found
    }
    

    public Page<Map<String, Object>> getAllUserHistory(Pageable pageable) {
    Page<Purchase> purchasePage = purchaseRepository.findAll(pageable);

    List<Map<String, Object>> historyList = purchasePage.stream().map(purchase -> {
        if (purchase.getUser() == null || purchase.getBook() == null) {
            return null;    
        }

        Map<String, Object> history = new HashMap<>();
        history.put("userId", purchase.getUser().getId());
        history.put("userName", purchase.getUser().getName());
        history.put("userEmail", purchase.getUser().getEmail());
        history.put("bookTitle", purchase.getBook().getTitle());
        history.put("bookAuthor", purchase.getBook().getAuthor());
        history.put("purchaseDate", purchase.getPurchaseDate());
        return history;
    }).filter(Objects::nonNull).collect(Collectors.toList());

    return new PageImpl<>(historyList, pageable, purchasePage.getTotalElements());
}

public boolean hasUserPurchasedBook(int userId, int bookId) {
    return purchaseRepository.existsByUserIdAndBookId(userId, bookId);
}

    
}

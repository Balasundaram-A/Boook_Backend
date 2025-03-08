package com.example.Book.Repo;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Book.Entity.Book;
import com.example.Book.Entity.Purchase;
import com.example.Book.Entity.User;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);

    boolean existsByUserAndBook(User user,Book book);

    
    @SuppressWarnings("null")
    Page<Purchase> findAll(Pageable pageable);

    boolean existsByUserIdAndBookId(int userId, int bookId);

    List<Purchase> findByPurchaseDateBetween(LocalDateTime from, LocalDateTime to);
}


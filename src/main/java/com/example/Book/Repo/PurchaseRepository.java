package com.example.Book.Repo;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Book.Entity.Book;
import com.example.Book.Entity.Purchase;
import com.example.Book.Entity.User;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);

    boolean existsByUserAndBook(User user,Book book);

    @Query("SELECT p.book FROM Purchase p WHERE p.user.id = :userId")
    List<Book> findBooksByUserId(@Param("userId") int userId);


    @SuppressWarnings("null")
    Page<Purchase> findAll(Pageable pageable);
}


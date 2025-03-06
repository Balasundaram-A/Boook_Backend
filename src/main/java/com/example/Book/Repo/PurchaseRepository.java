package com.example.Book.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Book.Entity.Book;
import com.example.Book.Entity.Purchase;
import com.example.Book.Entity.User;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);

    boolean existsByUserAndBook(User user,Book book);

}


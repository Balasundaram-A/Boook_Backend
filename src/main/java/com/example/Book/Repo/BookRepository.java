package com.example.Book.Repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Book.Entity.Book;

@Repository
public  interface BookRepository extends JpaRepository<Book, Integer>{

    List<Book> findByTitleContainingIgnoreCase(String title);

}

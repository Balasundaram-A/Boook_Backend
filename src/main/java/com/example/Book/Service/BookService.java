package com.example.Book.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Book.Entity.Book;
import com.example.Book.Repo.BookRepository;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;



    public List<Book> searchBooks(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public Book addBook(Book book, MultipartFile doc) throws IOException {

        book.setDocname(doc.getOriginalFilename());
        book.setDoctype(doc.getContentType());
        book.setDocdata(doc.getBytes());
        return bookRepository.save(book);
        
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void updateBook(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(int bookId) {
    
        return bookRepository.findById(bookId);
    }


}

package com.example.Book.Controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.Book.Entity.Book;
import com.example.Book.Service.BookService;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestPart Book book,@RequestPart MultipartFile doc) {

        try{
            Book book1 = bookService.addBook(book,doc);
            return new ResponseEntity<>(book1,HttpStatus.CREATED);
        }
        catch(Exception e)
        {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @GetMapping("/search/{title}")
    public List<Book> searchBooks(@PathVariable String title) {
        return bookService.searchBooks(title);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "Book updated successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }

    @GetMapping("/doc/{bookid}")
public ResponseEntity<byte[]> getdocByBookid(@PathVariable int bookid) {
    Optional<Book> optionalBook = bookService.findById(bookid);

    if (optionalBook.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    Book book = optionalBook.get(); // Extract the book from Optional

    if (book.getDocdata() == null) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    HttpHeaders headers = new HttpHeaders();
    
    try {
        headers.setContentType(MediaType.parseMediaType(book.getDoctype())); 
    } catch (Exception e) {
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Fallback MIME type
    }

    headers.setContentDisposition(ContentDisposition.inline().filename(book.getTitle() + ".pdf").build());

    return ResponseEntity.ok().headers(headers).body(book.getDocdata());
}

    

}

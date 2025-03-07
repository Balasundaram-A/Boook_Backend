package com.example.Book.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Book.Entity.Book;
import com.example.Book.Entity.User;
import com.example.Book.Service.PurchaseService;
import com.example.Book.Service.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable int id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User updateUser) {
        userService.updateUser(id, updateUser);
        return ResponseEntity.ok("User updated successfully!");
    }

    @DeleteMapping("/delete/{id}")

    public String deleteuser(@PathVariable int id) {
        userService.deleteuser(id);

        return "user deleted";
    }

    @PostMapping("/register")
    public String UserRegister(@RequestBody User user) {
        userService.register(user);

        return "added";
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name) {
        User user = userService.getuserbyname(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody User user) {
    System.out.println("Attempting login for Name: " + user.getName());

    boolean isAuthenticated = userService.authenticateUser(user.getName(), user.getPassword());
    
    if (isAuthenticated) {
        // ✅ Fetch user details from database after successful authentication
        User authenticatedUser = userService.getuserbyname(user.getName());
        
        if (authenticatedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", authenticatedUser.getId()); // ✅ Return the correct user ID
            response.put("name", authenticatedUser.getName());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid credentials"));
}
@GetMapping("/role/{id}")
public ResponseEntity<Map<String, String>> getUserRoleById(@PathVariable int id) {
    String role = userService.getRoleById(id);
    return ResponseEntity.ok(Collections.singletonMap("role", role)); // Returns { "role": "USER" }
}

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/{userId}/purchased-books")
    public ResponseEntity<List<Book>> getPurchasedBooks(@PathVariable int userId) {
        List<Book> purchasedBooks = purchaseService.getPurchasedBooksByUserId(userId);
        return ResponseEntity.ok(purchasedBooks);
    }
}

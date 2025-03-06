package com.example.Book.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Book.Entity.User;
import com.example.Book.Repo.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getuserbyname(String name) {
        return userRepository.findByName(name);
    }

    @Transactional
    public void updateUser(int id, User updateUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);

    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Use bean
        return userRepository.save(user);
    }

    public void deleteuser(int id) {

        userRepository.deleteById(id);
    }

    public boolean authenticateUser(String name, String password) {
        User user = userRepository.findByName(name);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public String getRoleById(int id) {
        return userRepository.getRoleById(id);
    }

}

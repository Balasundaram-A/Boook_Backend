package com.example.Book.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    

    @Column(nullable = false)
    private String role = "USER";

    public String getRole()
    {
        return role;
    }

    public  void setRole(String role)
    {
        this.role = role;
    }
}

package com.example.Book.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.Book.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByName(String name);
    
    @Query("SELECT u.role FROM User u WHERE u.id = :id")
    String getRoleById(@Param("id") int id);


}

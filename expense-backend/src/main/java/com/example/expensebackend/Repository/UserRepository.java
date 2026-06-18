package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.Role;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // Repository thao tac voi bang users, khoa chinh la Long.
    Optional<User> findByEmail(String email); // Spring Data JPA tu tao cau query tim user theo email.
}



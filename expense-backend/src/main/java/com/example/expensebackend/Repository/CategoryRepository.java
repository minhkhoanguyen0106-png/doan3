package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> { // Repository cho bang categories, khoa chinh Long.
    List<Category> findByUser(User user); // Spring Data JPA tu tao query lay category theo user.
}

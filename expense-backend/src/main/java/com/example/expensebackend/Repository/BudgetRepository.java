package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.Budget;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> { // Repository cho bang budgets, khoa chinh Long.
    List<Budget> findByUser(User user); // Spring Data JPA tu tao query lay budget theo user.
}

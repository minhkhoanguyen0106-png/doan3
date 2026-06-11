package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> { // Repository cho bang transactions, khoa chinh Long.
    List<Transaction> findByUser(User user); // Spring Data JPA tu tao query lay transaction theo user.
}

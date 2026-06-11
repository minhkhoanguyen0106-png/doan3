package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> { // Repository cho bang saving_goals.
    List<SavingGoal> findByUser(User user); // Spring Data JPA tu tao query lay saving goal theo user.
}

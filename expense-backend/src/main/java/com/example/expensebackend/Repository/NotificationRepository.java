package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.Notification;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> { // Repository cho bang notifications.
    List<Notification> findByUser(User user); // Spring Data JPA tu tao query lay notification theo user.
}

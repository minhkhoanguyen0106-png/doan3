package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.AiHistory;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiHistoryRepository extends JpaRepository<AiHistory, Long> { // Repository cho bang aiHistory.
    List<AiHistory> findByUser(User user); // Spring Data JPA tu tao query lay lich su AI theo user.

    User UserId(Long userId); // Method nay hien chua duoc service dung; nen xem lai sau neu can query theo user id.
}

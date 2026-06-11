package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.AiHistory;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.AiHistoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bao cho Spring biet day la service xu ly lich su AI.
public class AiHistoryService {
    private final AiHistoryRepository aiHistoryRepository; // Repository thao tac bang aiHistory.
    private final UserRepository userRepository; // Repository dung de tim user.

    // Constructor injection: Spring truyen repository vao service.
    public AiHistoryService(AiHistoryRepository aiHistoryRepository, UserRepository userRepository) {
        this.aiHistoryRepository = aiHistoryRepository; // Luu AiHistoryRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
    }

    // Tao ban ghi lich su AI moi cho user.
    public AiHistory createAiHistory(String email, AiHistory aiHistory) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        aiHistory.setUser(user); // Gan lich su AI thuoc ve user nay.
        return aiHistoryRepository.save(aiHistory); // Luu lich su vao database.
    }

    // Lay danh sach lich su AI cua user.
    public List<AiHistory> getAiHistoriesByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return aiHistoryRepository.findByUser(user); // Lay lich su AI theo user_id.
    }

    // Lay mot ban ghi lich su AI theo id.
    public AiHistory getAiHistoryById(Long id) {
        return aiHistoryRepository.findById(id) // Tim lich su theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("AiHistory not found")); // Khong thay thi bao loi.
    }

    // Xoa mot ban ghi lich su AI theo id.
    public void deleteAiHistory(Long id) {
        aiHistoryRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

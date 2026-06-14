package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.AiHistory;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.AiHistoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Request.AiHistoryRequest;
import com.example.expensebackend.dto.Response.AiHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public AiHistoryResponse createAiHistory(String email, AiHistoryRequest request) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
       AiHistory aiHistory = new AiHistory();
       aiHistory.setUser(user);
       aiHistory.setInputText(request.getInputText());
       aiHistory.setResult(request.getResult());
       aiHistory.setCreatedAt(request.getCreatedAt());

       AiHistory savedAiHistory = aiHistoryRepository.save(aiHistory);
       AiHistoryResponse response = new AiHistoryResponse();
       response.setUserName(savedAiHistory.getUser().getName());
       response.setInputText(savedAiHistory.getInputText());
       response.setResult(savedAiHistory.getResult());
       response.setCreatedAt(savedAiHistory.getCreatedAt());
       return response;
    }

    // Lay danh sach lich su AI cua user.
    public List<AiHistoryResponse> getAiHistoriesByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return aiHistoryRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Lay mot ban ghi lich su AI theo id.
    public AiHistoryResponse getAiHistoryById(Long id) {
        AiHistory aiHistory = aiHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AiHistory not found")); // Khong thay thi bao loi.
    return toResponse(aiHistory);
    }

    private AiHistoryResponse toResponse(AiHistory a) {
        return new AiHistoryResponse(
                a.getUser().getName(),
                a.getInputText(),
                a.getInputText(),
                a.getCreatedAt()
        );
    }

    // Xoa mot ban ghi lich su AI theo id.
    public void deleteAiHistory(Long id) {
        aiHistoryRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.AiHistoryService;
import com.example.expensebackend.dto.Request.AiHistoryRequest;
import com.example.expensebackend.dto.Response.AiHistoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la REST controller tra JSON.
@RequestMapping("/api/ai-histories") // Tat ca API lich su AI bat dau bang /api/ai-histories.
public class AiHistoryController {
    private final AiHistoryService aiHistoryService; // Service xu ly logic lich su AI.

    // Constructor injection: Spring tu truyen AiHistoryService vao controller.
    public AiHistoryController(AiHistoryService aiHistoryService) {
        this.aiHistoryService = aiHistoryService; // Luu service vao field.
    }

    @PostMapping("/user/{email}") // Tao mot ban ghi lich su AI cho user.
    public AiHistoryResponse createAiHistory(@PathVariable String email, @RequestBody AiHistoryRequest request) {
        return aiHistoryService.createAiHistory(email, request); // Goi service gan user va luu lich su.
    }

    @GetMapping("/user/{email}") // Lay lich su AI cua user theo email.
    public List<AiHistoryResponse> getAiHistoriesByEmail(@PathVariable String email) {
        return aiHistoryService.getAiHistoriesByEmail(email); // Goi service lay lich su theo user.
    }

    @GetMapping("/{id}") // Lay chi tiet mot lich su AI theo id.
    public AiHistoryResponse getAiHistoryById(@PathVariable Long id) {
        return aiHistoryService.getAiHistoryById(id); // Goi service tim lich su theo id.
    }

    @DeleteMapping("/{id}") // Xoa lich su AI theo id.
    public void deleteAiHistory(@PathVariable Long id) {
        aiHistoryService.deleteAiHistory(id); // Goi service xoa lich su trong database.
    }
}

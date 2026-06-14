package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.SavingGoalService;
import com.example.expensebackend.dto.Request.SavingGoalRequest;
import com.example.expensebackend.dto.Response.SavingGoalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class SavingGoalController {
    private final SavingGoalService savingGoalService;

    public SavingGoalController(SavingGoalService savingGoalService) {
        this.savingGoalService = savingGoalService;
    }

    @PostMapping("/user/{email}")
    public SavingGoalResponse createSavingGoal(@PathVariable String email, @RequestBody SavingGoalRequest request) {
        return savingGoalService.createSavingGoal(email, request);
    }

    @GetMapping("/user/{email}")
    public List<SavingGoalResponse> getSavingGoalsByEmail(@PathVariable String email) {
        return savingGoalService.getSavingGoalsByEmail(email);
    }

    @GetMapping("/{id}")
    public SavingGoalResponse getSavingGoalById(@PathVariable Long id) {
        return savingGoalService.getSavingGoalById(id);
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<Double> getProgress(@PathVariable Long id) {
        return ResponseEntity.ok(savingGoalService.getProgress(id));
    }

    @PutMapping("/{id}")
    public SavingGoalResponse updateSavingGoal(@PathVariable Long id, @RequestBody SavingGoalRequest request) {
        return savingGoalService.updateSavingGoal(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSavingGoal(@PathVariable Long id) {
        savingGoalService.deleteSavingGoal(id);
    }
}

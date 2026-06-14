package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.SavingGoalRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Request.SavingGoalRequest;
import com.example.expensebackend.dto.Response.SavingGoalResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingGoalService {
    private final SavingGoalRepository savingGoalRepository;
    private final UserRepository userRepository;

    public SavingGoalService(SavingGoalRepository savingGoalRepository, UserRepository userRepository) {
        this.savingGoalRepository = savingGoalRepository;
        this.userRepository = userRepository;
    }

    public SavingGoalResponse createSavingGoal(String email, SavingGoalRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SavingGoal savingGoal = new SavingGoal();
        savingGoal.setUser(user);
        savingGoal.setGoalName(request.getGoalName());
        savingGoal.setTargetAmount(request.getTargetAmount());
        savingGoal.setCurrentAmount(request.getCurrentAmount());
        savingGoal.setDeadline(request.getDeadline());
        savingGoal.setStatus(request.getStatus());
        return toResponse(savingGoalRepository.save(savingGoal));
    }

    public List<SavingGoalResponse> getSavingGoalsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return savingGoalRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SavingGoalResponse getSavingGoalById(Long id) {
        SavingGoal goal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saving goal not found"));
        return toResponse(goal);
    }

    public SavingGoalResponse updateSavingGoal(Long id, SavingGoalRequest request) {
        SavingGoal savingGoal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saving goal not found"));
        savingGoal.setGoalName(request.getGoalName());
        savingGoal.setTargetAmount(request.getTargetAmount());
        savingGoal.setCurrentAmount(request.getCurrentAmount());
        savingGoal.setDeadline(request.getDeadline());
        savingGoal.setStatus(request.getStatus());
        return toResponse(savingGoalRepository.save(savingGoal));
    }

    public void deleteSavingGoal(Long id) {
        savingGoalRepository.deleteById(id);
    }

    public double getProgress(Long id) {
        SavingGoal goal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saving goal not found"));
        if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) == 0) return 0;
        return goal.getCurrentAmount()
                .divide(goal.getTargetAmount(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    private SavingGoalResponse toResponse(SavingGoal goal) {
        double progress = getProgress(goal.getId());
        return new SavingGoalResponse(
                goal.getGoalName(),
                goal.getUser().getName(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                goal.getDeadline(),
                goal.getStatus(),
                progress
        );
    }
}

package com.example.expensebackend.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingGoalResponse {
    private String goalName;
    private String userName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate deadline;
    private String status;
    private double progress;

    public SavingGoalResponse(String goalName, String userName, BigDecimal targetAmount, BigDecimal currentAmount, LocalDate deadline, String status, double progress) {
        this.goalName = goalName;
        this.userName = userName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
        this.progress = progress;
    }

    public String getGoalName() { return goalName; }
    public String getUserName() { return userName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public double getProgress() { return progress; }
}

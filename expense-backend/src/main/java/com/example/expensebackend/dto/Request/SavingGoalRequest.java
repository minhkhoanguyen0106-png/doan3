package com.example.expensebackend.dto.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingGoalRequest {
    private String goalName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate deadline;
    private String status;

    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }

    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setStatus(String status) { this.status = status; }
}

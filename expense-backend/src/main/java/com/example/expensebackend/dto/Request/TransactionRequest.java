package com.example.expensebackend.dto.Request;

import com.example.expensebackend.Entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequest {
    private Long categoryId;
    private String title;
    private BigDecimal amount;
    private TransactionType type;
    private String note;
    private LocalDateTime transactionDate;

    public Long getCategoryId() { return categoryId; }
    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getNote() { return note; }
    public LocalDateTime getTransactionDate() { return transactionDate; }

    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public void setTitle(String title) { this.title = title; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(TransactionType type) { this.type = type; }
    public void setNote(String note) { this.note = note; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
}

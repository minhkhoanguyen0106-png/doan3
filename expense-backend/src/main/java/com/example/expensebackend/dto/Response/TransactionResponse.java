package com.example.expensebackend.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long categoryId;
    private String title;
    private BigDecimal amount;
    private String transactionType;
    private String note;
    private LocalDateTime transactionDate;

    public TransactionResponse(Long categoryId, String title, BigDecimal amount, String transactionType, String note, LocalDateTime transactionDate){
        this.categoryId = categoryId;
        this.title = title;
        this.amount = amount;
        this.transactionType = transactionType;
        this.note = note;
        this.transactionDate = transactionDate;
    }

    private Long getCategoryId() {return  categoryId;}
    private String getTitle() {return title;}
    private BigDecimal getAmount() {return amount;}
    private String getTransactionType() {return transactionType;}
    private String getNote() {return note;}
    private LocalDateTime getTransactionDate() {return  transactionDate;}

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}


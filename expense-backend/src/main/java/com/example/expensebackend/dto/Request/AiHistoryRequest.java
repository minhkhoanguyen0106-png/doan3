package com.example.expensebackend.dto.Request;

import java.time.LocalDate;

public class AiHistoryRequest {
    private String userName;
    private String inputText;
    private String result;
    private LocalDate createdAt = LocalDate.now();

    public String getUserName() {
        return userName;
    }

    public String getInputText() {
        return inputText;
    }

    public String getResult() {
        return result;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.expensebackend.dto.Response;

import java.time.LocalDate;

public class AiHistoryResponse {
    private String userName;
    private String inputText;
    private String result;
    private LocalDate createdAt = LocalDate.now();

    public AiHistoryResponse(String userName, String inputText, String result, LocalDate createdAt) {
        this.userName = userName;
        this.inputText = inputText;
        this.result = result;
        this.createdAt = createdAt;
    }
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
    public AiHistoryResponse() {}
}

package com.example.expensebackend.dto.Response;

import java.time.LocalDateTime;

public class NotificationResponse {
    private String userName;
    private String title;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt = LocalDateTime.now();


    public NotificationResponse(String userName, String title, String content, Boolean isRead, LocalDateTime createdAt) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public NotificationResponse() {}
}

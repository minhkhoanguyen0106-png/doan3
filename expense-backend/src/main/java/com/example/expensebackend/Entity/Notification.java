package com.example.expensebackend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity // Bao cho JPA biet Notification la mot entity.
@Table(name = "notifications") // Dat ten bang trong database la notifications.
public class Notification {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao notification.
    private Long id; // Id duy nhat cua thong bao.

    @ManyToOne // Nhieu thong bao co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id lien ket sang bang users.
    private User user; // User nhan thong bao.

    private String title; // Tieu de thong bao.

    private String content; // Noi dung thong bao.

    private Boolean isRead; // Trang thai da doc hay chua.

    private LocalDateTime createdAt = LocalDateTime.now(); // Mac dinh la thoi diem tao object.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setUser(User user) { this.user = user; } // Setter gan user.

    public void setTitle(String title) { this.title = title; } // Setter cap nhat tieu de.

    public void setContent(String content) { this.content = content; } // Setter cap nhat noi dung.

    public void setRead(Boolean read) { isRead = read; } // Setter cap nhat trang thai da doc.

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // Setter cap nhat ngay tao.

    public Long getId() { return id; } // Getter lay id.

    public User getUser() { return user; } // Getter lay user.

    public String getTitle() { return title; } // Getter lay tieu de.

    public String getContent() { return content; } // Getter lay noi dung.

    public Boolean getRead() { return isRead; } // Getter lay trang thai da doc.

    public LocalDateTime getCreatedAt() { return createdAt; } // Getter lay ngay tao.
}

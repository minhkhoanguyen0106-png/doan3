package com.example.expensebackend.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity // Bao cho JPA biet AiHistory la mot entity.
@Table(name = "aiHistory") // Dat ten bang trong database la aiHistory.
public class AiHistory {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao lich su.
    private Long id; // Id duy nhat cua ban ghi lich su AI.

    @ManyToOne // Nhieu lich su AI co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id lien ket sang bang users.
    private User user; // User so huu lich su AI nay.

    private String inputText; // Noi dung nguoi dung gui cho AI.

    private String result; // Ket qua AI tra ve.

    @Column(updatable = false) // Sau khi tao thi khong cho update cot createdAt.
    private LocalDate createdAt = LocalDate.now(); // Ngay tao ban ghi, mac dinh la hom nay.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setUser(User user) { this.user = user; } // Setter gan user.

    public void setInputText(String inputText) { this.inputText = inputText; } // Setter cap nhat input.

    public void setResult(String result) { this.result = result; } // Setter cap nhat ket qua.

    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; } // Setter cap nhat ngay tao tren object.

    public Long getId() { return id; } // Getter lay id.

    public User getUser() { return user; } // Getter lay user.

    public String getInputText() { return inputText; } // Getter lay input.

    public String getResult() { return result; } // Getter lay ket qua.

    public LocalDate getCreatedAt() { return createdAt; } // Getter lay ngay tao.
}

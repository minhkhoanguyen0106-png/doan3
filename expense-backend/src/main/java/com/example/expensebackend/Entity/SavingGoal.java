package com.example.expensebackend.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity // Bao cho JPA biet SavingGoal la mot entity.
@Table(name = "saving_goals") // Dat ten bang trong database la saving_goals.
public class SavingGoal {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao goal.
    private Long id; // Id duy nhat cua muc tieu.

    @Column(nullable = false) // Ten muc tieu bat buoc phai co.
    private String goalName; // Ten muc tieu tiet kiem.

    @Column(nullable = false) // So tien can dat bat buoc phai co.
    private BigDecimal targetAmount; // Muc tien can tiet kiem.

    @Column(nullable = false) // So tien hien co bat buoc phai co.
    private BigDecimal currentAmount; // So tien da tiet kiem duoc.

    @Column(nullable = false) // Han hoan thanh bat buoc phai co.
    private LocalDate deadline; // Ngay het han muc tieu.

    private String status; // Trang thai muc tieu, vi du active/done.

    @ManyToOne // Nhieu goal co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id lien ket sang bang users.
    private User user; // User so huu muc tieu nay.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setGoalName(String goalName) { this.goalName = goalName; } // Setter cap nhat ten muc tieu.

    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; } // Setter cap nhat tien muc tieu.

    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; } // Setter cap nhat tien hien co.

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; } // Setter cap nhat han.

    public void setStatus(String status) { this.status = status; } // Setter cap nhat trang thai.

    public void setUser(User user) { this.user = user; } // Setter gan user.

    public Long getId() { return id; } // Getter lay id.

    public String getGoalName() { return goalName; } // Getter lay ten muc tieu.

    public BigDecimal getTargetAmount() { return targetAmount; } // Getter lay tien muc tieu.

    public BigDecimal getCurrentAmount() { return currentAmount; } // Getter lay tien hien co.

    public LocalDate getDeadline() { return deadline; } // Getter lay han.

    public String getStatus() { return status; } // Getter lay trang thai.

    public User getUser() { return user; } // Getter lay user.
}

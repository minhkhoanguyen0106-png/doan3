package com.example.expensebackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity // Bao cho JPA biet Budget la mot entity.
@Table(name = "budgets") // Dat ten bang trong database la budgets.
public class Budget {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao budget.
    private Long id; // Id duy nhat cua budget.

    @ManyToOne // Nhieu budget co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id lien ket sang bang users.
    private User user; // User so huu budget nay.

    @ManyToOne // Nhieu budget co the gan voi mot category.
    @JoinColumn(name = "category_id") // Cot category_id lien ket sang bang categories.
    private Category category; // Danh muc ap dung ngan sach.

    @Column(nullable = false) // So tien gioi han bat buoc phai co.
    private BigDecimal limitAmount; // Muc ngan sach toi da.

    private Integer month; // Thang ap dung ngan sach.

    private Integer year; // Nam ap dung ngan sach.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setUser(User user) { this.user = user; } // Setter gan user.

    public void setCategory(Category category) { this.category = category; } // Setter gan category.

    public void setLimitAmount(BigDecimal limitAmount) { this.limitAmount = limitAmount; } // Setter cap nhat gioi han tien.

    public void setMonth(Integer month) { this.month = month; } // Setter cap nhat thang.

    public void setYear(Integer year) { this.year = year; } // Setter cap nhat nam.

    public Long getId() { return id; } // Getter lay id.

    public User getUser() { return user; } // Getter lay user.

    public Category getCategory() { return category; } // Getter lay category.

    public BigDecimal getLimitAmount() { return limitAmount; } // Getter lay gioi han tien.

    public Integer getMonth() { return month; } // Getter lay thang.

    public Integer getYear() { return year; } // Getter lay nam.
}

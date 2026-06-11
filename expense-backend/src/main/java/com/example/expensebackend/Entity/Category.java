package com.example.expensebackend.Entity;

import jakarta.persistence.*;

@Entity // Bao cho JPA biet Category la mot entity.
@Table(name = "categories") // Dat ten bang trong database la categories.
public class Category {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao category.
    private Long id; // Id duy nhat cua danh muc.

    @ManyToOne // Nhieu category co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id lien ket sang bang users.
    private User user; // User so huu danh muc nay.

    @Column(nullable = false) // Ten danh muc bat buoc phai co.
    private String categoryName; // Ten danh muc, vi du Food, Study, Shopping.

    @Enumerated(EnumType.STRING) // Luu enum thanh chu INCOME/EXPENSE trong database.
    private CategoryType type; // Loai danh muc: thu nhap hoac chi tieu.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setUser(User user) { this.user = user; } // Setter gan user so huu category.

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; } // Setter cap nhat ten danh muc.

    public void setType(CategoryType type) { this.type = type; } // Setter cap nhat loai danh muc.

    public Long getId() { return id; } // Getter lay id.

    public User getUser() { return user; } // Getter lay user so huu.

    public CategoryType getType() { return type; } // Getter lay loai danh muc.

    public String getCategoryName() { return categoryName; } // Getter lay ten danh muc.
}

package com.example.expensebackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity // Bao cho JPA biet class nay la mot bang trong database.
@Table(name = "transactions") // Dat ten bang la transactions.
public class Transaction {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi insert.
    private Long id; // Id duy nhat cua giao dich.

    @JsonIgnore
    @ManyToOne // Nhieu giao dich co the thuoc ve mot user.
    @JoinColumn(name = "user_id") // Cot user_id trong bang transactions lien ket sang bang users.
    private User user; // User so huu giao dich nay.

    @ManyToOne // Nhieu giao dich co the thuoc ve mot category.
    @JoinColumn(name = "category_id") // Cot category_id lien ket sang bang categories.
    private Category category; // Danh muc cua giao dich.

    @Column(nullable = false) // Title bat buoc phai co gia tri.
    private String title; // Ten hoac mo ta ngan cua giao dich.

    @Column(nullable = false) // Amount bat buoc phai co gia tri.
    private BigDecimal amount; // So tien, dung BigDecimal de tranh sai so so thuc.

    @Enumerated(EnumType.STRING) // Luu enum thanh chu INCOME/EXPENSE thay vi so.
    private TransactionType type; // Loai giao dich: thu nhap hoac chi tieu.

    private String note; // Ghi chu them, co the de trong.

    @Column(name = "transaction_date") // Ten cot trong database la transaction_date.
    private LocalDateTime transactionDate = LocalDateTime.now(); // Mac dinh la thoi diem tao object.

    public void setId(Long id) { this.id = id; } // Setter cho id.

    public void setUser(User user) { this.user = user; } // Setter gan user cho giao dich.

    public void setCategory(Category category) { this.category = category; } // Setter gan danh muc.

    public void setTitle(String title) { this.title = title; } // Setter cap nhat title.

    public void setAmount(BigDecimal amount) { this.amount = amount; } // Setter cap nhat so tien.

    public void setType(TransactionType type) { this.type = type; } // Setter cap nhat loai giao dich.

    public void setNote(String note) { this.note = note; } // Setter cap nhat ghi chu.

    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; } // Setter cap nhat ngay.

    public Long getId() { return id; } // Getter lay id.

    public User getUser() { return user; } // Getter lay user.

    public Category getCategory() { return category; } // Getter lay category.

    public String getTitle() { return title; } // Getter lay title.

    public BigDecimal getAmount() { return amount; } // Getter lay so tien.

    public TransactionType getType() { return type; } // Getter lay loai giao dich.

    public String getNote() { return note; } // Getter lay ghi chu.

    public LocalDateTime getTransactionDate() { return transactionDate; } // Getter lay ngay giao dich.

}

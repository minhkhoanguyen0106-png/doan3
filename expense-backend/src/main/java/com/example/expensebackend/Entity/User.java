package com.example.expensebackend.Entity;

import jakarta.persistence.*;

@Entity // Bao cho JPA biet User la mot entity map voi bang database.
@Table(name = "users") // Dat ten bang trong database la users.
public class User {
    @Id // Danh dau id la khoa chinh.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database tu tang id khi tao user moi.
    private Long id; // Id duy nhat cua user.

    @Column(nullable = false) // Name bat buoc phai co gia tri.
    private String name; // Ten nguoi dung.

    private String phoneNumber; // So dien thoai, co the de trong.

    @Column(nullable = false) // Password bat buoc phai co gia tri.
    private String password; // Mat khau da ma hoa, khong nen tra truc tiep ra API.

    private String address; // Dia chi nguoi dung, co the de trong.

    @Column(unique = true) // Email khong duoc trung trong bang users.
    private String email; // Email dung de dang nhap va tim user.

    public void setName(String name) { this.name = name; } // Setter cap nhat ten.

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; } // Setter cap nhat so dien thoai.

    public void setAddress(String address) { this.address = address; } // Setter cap nhat dia chi.

    public void setId(Long id) { this.id = id; } // Setter cap nhat id.

    public void setEmail(String email) { this.email = email; } // Setter cap nhat email.

    public void setPassword(String password) { this.password = password; } // Setter cap nhat password da ma hoa.

    public Long getId() { return id; } // Getter lay id.

    public String getName() { return name; } // Getter lay ten.

    public String getPhoneNumber() { return phoneNumber; } // Getter lay so dien thoai.

    public String getPassword() { return password; } // Getter lay password da ma hoa.

    public String getAddress() { return address; } // Getter lay dia chi.

    public String getEmail() { return email; } // Getter lay email.
}

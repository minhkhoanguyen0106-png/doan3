package com.example.expensebackend.dto.Request;

public class LoginRequest { // DTO nhan du lieu dang nhap tu client.
    private String email; // Email user nhap khi login.
    private String password; // Mat khau user nhap khi login.

    public String getEmail() { return email; } // Getter de controller/service doc email.
    public String getPassword() { return password; } // Getter de controller/service doc password.

    public void setEmail(String email) { this.email = email; } // Setter de Spring map JSON vao object.
    public void setPassword(String password) { this.password = password; } // Setter de Spring map JSON vao object.
}

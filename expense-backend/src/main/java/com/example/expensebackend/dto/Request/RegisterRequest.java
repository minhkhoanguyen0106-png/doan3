package com.example.expensebackend.dto.Request;

public class RegisterRequest { // DTO nhan du lieu dang ky tu client.
    private String name; // Ten user gui len khi dang ky.
    private String email; // Email user gui len khi dang ky.
    private String password; // Mat khau goc user gui len, service se ma hoa truoc khi luu.
    private String phoneNumber; // So dien thoai user gui len.
    private String address; // Dia chi user gui len.

    public String getName() { return name; } // Getter de service doc name.
    public String getEmail() { return email; } // Getter de service doc email.
    public String getPassword() { return password; } // Getter de service doc password.
    public String getPhoneNumber() { return phoneNumber; } // Getter de service doc phoneNumber.
    public String getAddress() { return address; } // Getter de service doc address.
}

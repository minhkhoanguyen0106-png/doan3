package com.example.expensebackend.dto.Response;

public class LoginResponse { // DTO tra ve sau khi login thanh cong.
    private String name; // Ten user tra ve cho client.
    private String email; // Email user tra ve cho client.
    private String phoneNumber; // So dien thoai tra ve cho client.
    private String address; // Dia chi tra ve cho client.

    // Constructor tao response tu thong tin user.
    public LoginResponse(String name, String email, String phoneNumber, String address) {
        this.name = name; // Gan name vao response.
        this.email = email; // Gan email vao response.
        this.phoneNumber = phoneNumber; // Gan phoneNumber vao response.
        this.address = address; // Gan address vao response.
    }

    public String getName() { return name; } // Getter de Jackson chuyen name sang JSON.
    public String getEmail() { return email; } // Getter de Jackson chuyen email sang JSON.
    public String getPhoneNumber() { return phoneNumber; } // Getter de Jackson chuyen phoneNumber sang JSON.
    public String getAddress() { return address; } // Getter de Jackson chuyen address sang JSON.
}

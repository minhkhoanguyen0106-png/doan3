package com.example.expensebackend.dto.Response;

public class RegisterResponse { // DTO tra ve sau khi dang ky thanh cong.
    private String name; // Ten user tra ve cho client.
    private String email; // Email user tra ve cho client.
    private String phoneNumber; // So dien thoai tra ve cho client.
    private String address; // Dia chi tra ve cho client.

    // Constructor co tham so dung khi muon tao response nhanh voi day du field.
    public RegisterResponse(String name, String email, String phoneNumber, String address) {
        this.name = name; // Gan name vao response.
        this.email = email; // Gan email vao response.
        this.phoneNumber = phoneNumber; // Gan phoneNumber vao response.
        this.address = address; // Gan address vao response.
    }

    public String getName() { return name; } // Getter de Jackson chuyen name sang JSON.
    public String getEmail() { return email; } // Getter de Jackson chuyen email sang JSON.
    public String getPhoneNumber() { return phoneNumber; } // Getter de Jackson chuyen phoneNumber sang JSON.
    public String getAddress() { return address; } // Getter de Jackson chuyen address sang JSON.

    public void setName(String name) { this.name = name; } // Setter gan name.

    public void setEmail(String email) { this.email = email; } // Setter gan email.

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; } // Setter gan phoneNumber.

    public void setAddress(String address) { this.address = address; } // Setter gan address.

    public RegisterResponse() {} // Constructor rong can cho viec tao object roi set tung field.
}

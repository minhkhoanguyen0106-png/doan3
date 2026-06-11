package com.example.expensebackend.dto.Request;

public class ChangePasswordRequest { // DTO dung de nhan du lieu doi mat khau tu client.
    private String oldPassword; // Mat khau cu ma user nhap de xac nhan.
    private String newPassword; // Mat khau moi user muon doi sang.

    public String getOldPassword() { return oldPassword; } // Getter de service doc oldPassword.
    public String getNewPassword() { return newPassword; } // Getter de service doc newPassword.

    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; } // Setter de Spring map JSON vao object.
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; } // Setter de Spring map JSON vao object.
}

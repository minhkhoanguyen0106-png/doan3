package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.AuthService;
import com.example.expensebackend.dto.Request.LoginRequest;
import com.example.expensebackend.dto.Request.RegisterRequest;
import com.example.expensebackend.dto.Response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register - Đăng ký tài khoản mới
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    // POST /api/auth/login - Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/auth/profile - Lấy thông tin profile người dùng hiện tại
    @GetMapping("/profile")
    public ResponseEntity<LoginResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();
        LoginResponse response = authService.getProfile(email);
        return ResponseEntity.ok(response);
    }

    // PUT /api/auth/profile - Cập nhật thông tin profile
    @PutMapping("/profile")
    public ResponseEntity<LoginResponse> updateProfile(
            Authentication authentication,
            @RequestBody Map<String, String> updates
    ) {
        String email = authentication.getName();
        LoginResponse response = authService.updateProfile(email, updates);
        return ResponseEntity.ok(response);
    }

    // PUT /api/auth/change-password - Đổi mật khẩu
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            Authentication authentication,
            @RequestBody Map<String, String> passwords
    ) {
        String email = authentication.getName();
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        Map<String, String> response = authService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.ok(response);
    }

    // POST /api/auth/logout - Đăng xuất (client sẽ xóa token)
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = Map.of("message", "Đăng xuất thành công");
        return ResponseEntity.ok(response);
    }
}

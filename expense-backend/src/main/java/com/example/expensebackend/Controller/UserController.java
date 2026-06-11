package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Service.UserService;
import com.example.expensebackend.dto.Reponse.LoginReponse;
import com.example.expensebackend.dto.Reponse.RegisterReponse;
import com.example.expensebackend.dto.Request.ChangePasswordRequest;
import com.example.expensebackend.dto.Request.LoginRequest;
import com.example.expensebackend.dto.Request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la controller tra ve du lieu dang JSON.
@RequestMapping("/api/users") // Tat ca API trong class nay deu bat dau bang /api/users.
public class UserController {
    private final UserService userService; // Controller can UserService de goi logic xu ly user.

    // Constructor injection: Spring tu truyen UserService vao khi tao UserController.
    public UserController(UserService userService) {
        this.userService = userService; // Luu UserService vao field de cac ham ben duoi su dung.
    }

    @PostMapping("/register") // API dang ky user moi: POST /api/users/register.
    public RegisterReponse register(@RequestBody RegisterRequest request) {
        // @RequestBody lay JSON tu client va chuyen thanh RegisterRequest.
        return userService.register(request); // Dua request qua service de tao user va tra response.
    }

    @PostMapping("/login") // API dang nhap: POST /api/users/login.
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Goi service de kiem tra email va password.
        return userService.login(request.getEmail(), request.getPassword())
                // Neu login dung, tao LoginReponse de tra ve thong tin user can thiet.
                .map(u -> ResponseEntity.ok(new LoginReponse(
                    u.getName(), u.getEmail(), // Lay ten va email tu user trong database.
                    u.getPhoneNumber(), u.getAddress() // Lay so dien thoai va dia chi.
                )))
                // Neu login sai, tra HTTP 400 Bad Request.
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping // API lay tat ca user: GET /api/users.
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Goi service lay danh sach user tu database.
    }

    @GetMapping("/{id}") // API lay user theo id: GET /api/users/{id}.
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // @PathVariable lay id tren URL va gan vao bien id.
        return userService.getUserById(id)
                // Neu tim thay user thi tra HTTP 200 kem user.
                .map(ResponseEntity::ok)
                // Neu khong tim thay user thi tra HTTP 404 Not Found.
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}/change-password") // API doi mat khau bang email.
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody ChangePasswordRequest request) {
        // email lay tu URL, request lay tu JSON body gom oldPassword va newPassword.
        try {
            userService.changePassword(email, request); // Dua email va request sang service de xu ly doi mat khau.
            return ResponseEntity.ok("Password changed successfully"); // Doi thanh cong thi tra HTTP 200.
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Co loi thi tra HTTP 400 kem thong bao loi.
        }
    }

    @PutMapping("/{id}") // API cap nhat user theo id.
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user); // Goi service de cap nhat thong tin user.
    }

    @DeleteMapping("/{id}") // API xoa user theo id.
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Goi service de xoa user trong database.
    }
}

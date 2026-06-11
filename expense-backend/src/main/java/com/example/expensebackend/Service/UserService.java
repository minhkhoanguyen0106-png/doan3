package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.CategoryType;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Reponse.RegisterReponse;
import com.example.expensebackend.dto.Request.ChangePasswordRequest;
import com.example.expensebackend.dto.Request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Bao cho Spring biet day la class chua logic xu ly nghiep vu.
public class UserService {
    private final UserRepository userRepository; // Repository dung de thao tac bang users.
    private final PasswordEncoder passwordEncoder; // Dung de ma hoa va kiem tra mat khau.
    private final CategoryRepository categoryRepository; // Dung de tao danh muc mac dinh cho user moi.

    // Constructor injection: Spring tu truyen cac repository va passwordEncoder vao service.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository){
        this.userRepository = userRepository; // Luu UserRepository vao field.
        this.passwordEncoder = passwordEncoder; // Luu PasswordEncoder vao field.
        this.categoryRepository = categoryRepository; // Luu CategoryRepository vao field.
    }

    // Ham dang ky user moi.
    public RegisterReponse register(RegisterRequest request) {
        // Kiem tra email da ton tai chua de tranh trung tai khoan.
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("Email already exists"); // Neu email da co thi bao loi.

        // Khong luu mat khau that vao database, phai ma hoa truoc khi luu.
        String hashPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(); // Tao entity User moi de luu vao database.
        user.setName(request.getName()); // Lay ten tu request gan vao user.
        user.setEmail(request.getEmail()); // Lay email tu request gan vao user.
        user.setPassword(hashPassword); // Gan mat khau da ma hoa.
        user.setAddress(request.getAddress()); // Lay dia chi tu request.
        user.setPhoneNumber(request.getPhoneNumber()); // Lay so dien thoai tu request.

        User savedUser = userRepository.save(user); // Luu user vao database va nhan lai user da co id.
        RegisterReponse reponse = new RegisterReponse(); // Tao DTO response de tra ve cho client.
        reponse.setName(savedUser.getName()); // Tra ve ten user.
        reponse.setEmail(savedUser.getEmail()); // Tra ve email user.
        reponse.setPhoneNumber(savedUser.getPhoneNumber()); // Tra ve so dien thoai.
        reponse.setAddress(savedUser.getAddress()); // Tra ve dia chi.

        // Khi dang ky xong thi tao san cac danh muc chi tieu co ban cho user.
        List<String> defaultCategories = List.of(
                "An uong", "Di lai"
        );

        // Lap qua tung ten danh muc de tao Category rieng cho user vua dang ky.
        for (String name : defaultCategories) {
            Category cat = new Category(); // Tao entity Category moi.
            cat.setCategoryName(name); // Gan ten danh muc.
            cat.setType(CategoryType.EXPENSE); // Danh muc mac dinh la loai chi tieu.
            cat.setUser(savedUser); // Gan danh muc nay thuoc ve user vua tao.
            categoryRepository.save(cat); // Luu danh muc vao database.
        }

        return reponse; // Tra response dang ky ve controller.
    }

    // Ham doi mat khau: tim user bang email, kiem tra mat khau cu, roi luu mat khau moi.
    public void changePassword(String email, ChangePasswordRequest request) {
        // Tim user theo email tren URL.
        User user = userRepository.findByEmail(email)
                // Neu khong co user thi nem loi de controller tra Bad Request.
                .orElseThrow(() -> new RuntimeException("User not found"));

        // So sanh oldPassword nguoi dung nhap voi password da ma hoa trong database.
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new RuntimeException("old password incorrect"); // Sai mat khau cu thi khong cho doi.

        user.setPassword(passwordEncoder.encode(request.getNewPassword())); // Ma hoa mat khau moi roi gan vao user.
        userRepository.save(user); // Luu user da doi mat khau vao database.
    }

    // Ham dang nhap: tra Optional<User>, co user neu dung email/password, rong neu sai.
    public Optional<User> login(String email, String password) {
        // Tim user theo email, sau do filter chi giu lai user neu password dung.
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    // Lay tat ca user trong bang users.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Tim user theo khoa chinh id.
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Cap nhat thong tin user theo id.
    public User updateUser(Long id, User newUser) {
        // Tim user can cap nhat, neu khong thay thi bao loi.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(newUser.getName()); // Cap nhat ten.
        user.setPhoneNumber(newUser.getPhoneNumber()); // Cap nhat so dien thoai.
        user.setAddress(newUser.getAddress()); // Cap nhat dia chi.
        user.setEmail(newUser.getEmail()); // Cap nhat email.

        return userRepository.save(user); // Luu thay doi va tra user da cap nhat.
    }

    // Xoa user theo id.
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

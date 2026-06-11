package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bao cho Spring biet day la service xu ly nghiep vu danh muc.
public class CategoryService {
    private final CategoryRepository categoryRepository; // Repository thao tac bang categories.
    private final UserRepository userRepository; // Repository dung de tim user theo email.

    // Constructor injection: Spring truyen repository vao service.
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository; // Luu CategoryRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
    }

    // Tao category moi cho user co email tu URL.
    public Category createCategory(String email, Category category) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong co user thi bao loi.
        category.setUser(user); // Gan category nay thuoc ve user tim duoc.
        return categoryRepository.save(category); // Luu category vao database.
    }

    // Lay danh sach category cua mot user.
    public List<Category> getCategoriesByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong co user thi bao loi.
        return categoryRepository.findByUser(user); // Lay cac category co user_id cua user nay.
    }

    // Lay category theo id.
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id) // Tim category theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("Category not found")); // Khong thay thi bao loi.
    }

    // Cap nhat category theo id.
    public Category updateCategory(Long id, Category newCategory) {
        Category category = categoryRepository.findById(id) // Lay category cu tu database.
                .orElseThrow(() -> new RuntimeException("Category not found")); // Khong thay thi bao loi.
        category.setCategoryName(newCategory.getCategoryName()); // Cap nhat ten danh muc.
        category.setType(newCategory.getType()); // Cap nhat loai danh muc thu/chi.
        return categoryRepository.save(category); // Luu thay doi va tra category moi.
    }

    // Xoa category theo id.
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

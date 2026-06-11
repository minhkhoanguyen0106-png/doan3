package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Budget;
import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.BudgetRepository;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bao cho Spring biet day la service xu ly logic ngan sach.
public class BudgetService {
    private final BudgetRepository budgetRepository; // Repository thao tac bang budgets.
    private final UserRepository userRepository; // Repository dung de tim user.
    private final CategoryRepository categoryRepository; // Repository dung de tim category.

    // Constructor injection: Spring truyen cac repository vao service.
    public BudgetService(
            BudgetRepository budgetRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.budgetRepository = budgetRepository; // Luu BudgetRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
        this.categoryRepository = categoryRepository; // Luu CategoryRepository vao field.
    }

    // Tao ngan sach cho mot user va mot category.
    public Budget createBudget(String email, Long categoryId, Budget budget) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        Category category = categoryRepository.findById(categoryId) // Tim category theo id.
                .orElseThrow(() -> new RuntimeException("Category not found")); // Khong thay category thi bao loi.
        budget.setUser(user); // Gan budget thuoc ve user nay.
        budget.setCategory(category); // Gan budget ap dung cho category nay.
        return budgetRepository.save(budget); // Luu budget vao database.
    }

    // Lay danh sach budget cua user theo email.
    public List<Budget> getBudgetsByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return budgetRepository.findByUser(user); // Lay cac budget co user_id cua user nay.
    }

    // Lay budget theo id.
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id) // Tim budget theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("Budget not found")); // Khong thay thi bao loi.
    }

    // Cap nhat budget theo id.
    public Budget updateBudget(Long id, Budget newBudget) {
        Budget budget = budgetRepository.findById(id) // Lay budget cu trong database.
                .orElseThrow(() -> new RuntimeException("Budget not found")); // Khong thay thi bao loi.
        budget.setLimitAmount(newBudget.getLimitAmount()); // Cap nhat so tien gioi han.
        budget.setMonth(newBudget.getMonth()); // Cap nhat thang ap dung.
        budget.setYear(newBudget.getYear()); // Cap nhat nam ap dung.
        return budgetRepository.save(budget); // Luu thay doi va tra budget moi.
    }

    // Xoa budget theo id.
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

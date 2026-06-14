package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Budget;
import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.BudgetRepository;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Request.BudgetRequest;
import com.example.expensebackend.dto.Response.BudgetResponse;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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
    public BudgetResponse createBudget(String email, Long categoryId, BudgetRequest request) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        Category category = categoryRepository.findById(categoryId) // Tim category theo id.
                .orElseThrow(() -> new RuntimeException("Category not found")); // Khong thay category thi bao loi.
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setLimitAmount(request.getLimitAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        Budget savedBudget = budgetRepository.save(budget);
        BudgetResponse response = new BudgetResponse();
        response.setUserName(savedBudget.getUser().getName());
        response.setCategoryName(savedBudget.getCategory().getCategoryName());
        response.setLimitAmount(savedBudget.getLimitAmount());
        response.setMonth(savedBudget.getMonth());
        response.setYear(savedBudget.getYear());

        return response;

    }

    // Lay danh sach budget cua user theo email.
    public List<BudgetResponse> getBudgetsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return budgetRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Lay budget theo id.
    public BudgetResponse getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        return toResponse(budget);
    }

    private BudgetResponse toResponse(Budget b) {
        return new BudgetResponse(
                b.getUser().getName(),
                b.getCategory().getCategoryName(),
                b.getLimitAmount(),
                b.getMonth(),
                b.getYear()
        );
    }

    // Cap nhat budget theo id.
    public BudgetResponse updateBudget(Long id, BudgetRequest request) {
        Budget budget = budgetRepository.findById(id) // Lay budget cu trong database.
                .orElseThrow(() -> new RuntimeException("Budget not found")); // Khong thay thi bao loi.
        budget.setLimitAmount(request.getLimitAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        return toResponse(budgetRepository.save(budget)); // Luu thay doi va tra budget moi.
    }

    // Xoa budget theo id.
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

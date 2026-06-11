package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Budget;
import com.example.expensebackend.Service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la REST controller tra JSON.
@RequestMapping("/api/budgets") // Tat ca API ngan sach bat dau bang /api/budgets.
public class BudgetController {
    private final BudgetService budgetService; // Service xu ly logic ngan sach.

    // Constructor injection: Spring tu truyen BudgetService vao controller.
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService; // Luu service vao field.
    }

    @PostMapping("/user/{email}/category/{categoryId}") // Tao ngan sach cho user va category.
    public Budget createBudget(
            @PathVariable String email, // Lay email user tu URL.
            @PathVariable Long categoryId, // Lay id category tu URL.
            @RequestBody Budget budget // Lay JSON body va map thanh Budget.
    ) {
        return budgetService.createBudget(email, categoryId, budget); // Goi service gan user/category va luu.
    }

    @GetMapping("/user/{email}") // Lay danh sach ngan sach cua user.
    public List<Budget> getBudgetsByEmail(@PathVariable String email) {
        return budgetService.getBudgetsByEmail(email); // Goi service lay budget theo user.
    }

    @GetMapping("/{id}") // Lay chi tiet ngan sach theo id.
    public Budget getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id); // Goi service tim budget theo id.
    }

    @PutMapping("/{id}") // Cap nhat ngan sach theo id.
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget); // Goi service cap nhat limit/thang/nam.
    }

    @DeleteMapping("/{id}") // Xoa ngan sach theo id.
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id); // Goi service xoa budget trong database.
    }
}

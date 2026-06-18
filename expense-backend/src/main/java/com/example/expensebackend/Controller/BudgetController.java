package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.BudgetService;
import com.example.expensebackend.dto.Request.BudgetRequest;
import com.example.expensebackend.dto.Response.BudgetResponse;
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
    public BudgetResponse createBudget(
            @PathVariable String email, // Lay email user tu URL.
            @PathVariable Long categoryId, // Lay id category tu URL.
            @RequestBody BudgetRequest budgetRequest // Lay JSON body va map thanh Budget.
    ) {
        return budgetService.createBudget(email, categoryId, budgetRequest); // Goi service gan user/category va luu.
    }

    @GetMapping("/user/{email}") // Lay danh sach ngan sach cua user.
    public List<BudgetResponse> getBudgetsByEmail(@PathVariable String email) {
        return budgetService.getBudgetsByEmail(email); // Goi service lay budget theo user.
    }

    @GetMapping("/{id}") // Lay chi tiet ngan sach theo id.
    public BudgetResponse getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id); // Goi service tim budget theo id.
    }

    @PutMapping("/{id}") // Cap nhat ngan sach theo id.
    public BudgetResponse updateBudget(@PathVariable Long id, @RequestBody BudgetRequest request) {
        return budgetService.updateBudget(id, request); // Goi service cap nhat limit/thang/nam.
    }

    @DeleteMapping("/{id}") // Xoa ngan sach theo id.
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id); // Goi service xoa budget trong database.
    }
}

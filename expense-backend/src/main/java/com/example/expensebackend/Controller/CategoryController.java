package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la REST controller tra ve JSON.
@RequestMapping("/api/categories") // Tat ca API danh muc bat dau bang /api/categories.
public class CategoryController {
    private final CategoryService categoryService; // Service xu ly logic danh muc.

    // Constructor injection: Spring tu truyen CategoryService vao controller.
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService; // Luu service vao field.
    }

    @PostMapping("/user/{email}") // Tao danh muc moi cho user theo email.
    public Category createCategory(@PathVariable String email, @RequestBody Category category) {
        return categoryService.createCategory(email, category); // Goi service gan user va luu category.
    }

    @GetMapping("/user/{email}") // Lay tat ca danh muc cua user theo email.
    public List<Category> getCategoriesByEmail(@PathVariable String email) {
        return categoryService.getCategoriesByEmail(email); // Goi service tim user va lay category cua user.
    }

    @GetMapping("/{id}") // Lay chi tiet mot danh muc theo id.
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id); // Goi service tim category theo id.
    }

    @PutMapping("/{id}") // Cap nhat danh muc theo id.
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category); // Goi service cap nhat ten va type cua category.
    }

    @DeleteMapping("/{id}") // Xoa danh muc theo id.
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id); // Goi service xoa category trong database.
    }
}

package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Service.SavingGoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la REST controller tra ve JSON.
@RequestMapping("/api/goals") // Tat ca API muc tieu tiet kiem bat dau bang /api/goals.
public class SavingGoalController {
    private final SavingGoalService savingGoalService; // Service xu ly logic muc tieu tiet kiem.

    // Constructor injection: Spring tu truyen SavingGoalService vao controller.
    public SavingGoalController(SavingGoalService savingGoalService) {
        this.savingGoalService = savingGoalService; // Luu service vao field.
    }

    @PostMapping("/user/{email}") // Tao muc tieu tiet kiem cho user theo email.
    public SavingGoal createSavingGoal(@PathVariable String email, @RequestBody SavingGoal savingGoal) {
        return savingGoalService.createSavingGoal(email, savingGoal); // Goi service gan user va luu goal.
    }

    @GetMapping("/user/{email}") // Lay danh sach muc tieu cua user.
    public List<SavingGoal> getSavingGoalsByEmail(@PathVariable String email) {
        return savingGoalService.getSavingGoalsByEmail(email); // Goi service lay goal theo user.
    }

    @GetMapping("/{id}") // Lay chi tiet mot muc tieu theo id.
    public SavingGoal getSavingGoalById(@PathVariable Long id) {
        return savingGoalService.getSavingGoalById(id); // Goi service tim goal theo id.
    }

    @GetMapping("/{id}/progress") // Lay phan tram hoan thanh cua muc tieu.
    public ResponseEntity<Double> getProgress(@PathVariable Long id) {
        return ResponseEntity.ok(savingGoalService.getProgress(id)); // Tra HTTP 200 kem so phan tram.
    }

    @PutMapping("/{id}") // Cap nhat muc tieu theo id.
    public SavingGoal updateSavingGoal(@PathVariable Long id, @RequestBody SavingGoal savingGoal) {
        return savingGoalService.updateSavingGoal(id, savingGoal); // Goi service cap nhat thong tin goal.
    }

    @DeleteMapping("/{id}") // Xoa muc tieu theo id.
    public void deleteSavingGoal(@PathVariable Long id) {
        savingGoalService.deleteSavingGoal(id); // Goi service xoa goal trong database.
    }
}

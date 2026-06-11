package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.StatisticService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // Bao cho Spring biet day la REST controller tra ve JSON.
@RequestMapping("/api/statistics") // Tat ca API thong ke bat dau bang /api/statistics.
public class StatisticController {
    private final StatisticService statisticService; // Service xu ly logic thong ke.

    // Constructor injection: Spring tu truyen StatisticService vao controller.
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService; // Luu service vao field.
    }

    @GetMapping("/summary/user/{email}") // API tong hop thu, chi, so du cua user.
    public Map<String, Object> getSummary(@PathVariable String email) {
        return statisticService.getSummary(email); // Goi service tinh tong thu, tong chi, balance.
    }

    @GetMapping("/monthly/user/{email}") // API thong ke thu/chi theo tung thang trong mot nam.
    public Map<String, Object> getMonthly(@PathVariable String email, @RequestParam int year) {
        return statisticService.getMonthlySummary(email, year); // year lay tu query param, vi du ?year=2026.
    }

    @GetMapping("/by-category/user/{email}") // API thong ke chi tieu theo danh muc.
    public Map<String, Object> getByCategory(@PathVariable String email) {
        return statisticService.getByCategory(email); // Goi service gom tong chi theo category.
    }
}

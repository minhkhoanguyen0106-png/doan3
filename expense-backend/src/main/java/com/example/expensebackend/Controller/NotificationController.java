package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Notification;
import com.example.expensebackend.Service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet day la REST controller tra JSON.
@RequestMapping("/api/notifications") // Tat ca API thong bao bat dau bang /api/notifications.
public class NotificationController {
    private final NotificationService notificationService; // Service xu ly logic thong bao.

    // Constructor injection: Spring tu truyen NotificationService vao controller.
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService; // Luu service vao field.
    }

    @PostMapping("/user/{email}") // Tao thong bao cho user theo email.
    public Notification createNotification(@PathVariable String email, @RequestBody Notification notification) {
        return notificationService.createNotification(email, notification); // Goi service gan user va luu notification.
    }

    @GetMapping("/user/{email}") // Lay danh sach thong bao cua user.
    public List<Notification> getNotificationsByEmail(@PathVariable String email) {
        return notificationService.getNotificationsByEmail(email); // Goi service lay notification theo user.
    }

    @GetMapping("/{id}") // Lay chi tiet thong bao theo id.
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id); // Goi service tim notification theo id.
    }

    @PutMapping("/{id}") // Cap nhat thong bao theo id.
    public Notification updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification); // Goi service cap nhat title/content/read.
    }

    @DeleteMapping("/{id}") // Xoa thong bao theo id.
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id); // Goi service xoa notification trong database.
    }
}

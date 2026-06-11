package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Notification;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.NotificationRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bao cho Spring biet day la service xu ly thong bao.
public class NotificationService {
    private final NotificationRepository notificationRepository; // Repository thao tac bang notifications.
    private final UserRepository userRepository; // Repository dung de tim user.

    // Constructor injection: Spring truyen repository vao service.
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository; // Luu NotificationRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
    }

    // Tao thong bao moi cho user.
    public Notification createNotification(String email, Notification notification) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        notification.setUser(user); // Gan thong bao thuoc ve user nay.
        return notificationRepository.save(notification); // Luu thong bao vao database.
    }

    // Lay danh sach thong bao cua user.
    public List<Notification> getNotificationsByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return notificationRepository.findByUser(user); // Lay notification theo user_id.
    }

    // Lay thong bao theo id.
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id) // Tim notification theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("Notification not found")); // Khong thay thi bao loi.
    }

    // Cap nhat thong bao theo id.
    public Notification updateNotification(Long id, Notification newNotification) {
        Notification notification = notificationRepository.findById(id) // Lay notification cu.
                .orElseThrow(() -> new RuntimeException("Notification not found")); // Khong thay thi bao loi.
        notification.setTitle(newNotification.getTitle()); // Cap nhat tieu de.
        notification.setContent(newNotification.getContent()); // Cap nhat noi dung.
        notification.setRead(newNotification.getRead()); // Cap nhat trang thai da doc/chua doc.
        notification.setCreatedAt(newNotification.getCreatedAt()); // Cap nhat thoi gian tao neu client gui len.
        return notificationRepository.save(notification); // Luu thay doi va tra notification moi.
    }

    // Xoa thong bao theo id.
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

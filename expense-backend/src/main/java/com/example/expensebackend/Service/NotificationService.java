package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Notification;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.NotificationRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Request.NotificationRequest;
import com.example.expensebackend.dto.Response.NotificationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public NotificationResponse createNotification(String email, NotificationRequest request) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
       Notification notification = new Notification();
       notification.setUser(user);
       notification.setTitle(request.getTitle());
       notification.setContent(request.getContent());
       notification.setRead(request.getRead());
       notification.setCreatedAt(request.getCreatedAt());

       Notification savedNotification = notificationRepository.save(notification);
       NotificationResponse response = new NotificationResponse();
       response.setUserName(savedNotification.getUser().getName());
       response.setTitle(savedNotification.getTitle());
       response.setContent(savedNotification.getContent());
       response.setRead(savedNotification.getRead());
       response.setCreatedAt(savedNotification.getCreatedAt());

       return response;

    }

    // Lay danh sach thong bao cua user.
    public List<NotificationResponse> getNotificationsByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return notificationRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Lay thong bao theo id.
    public NotificationResponse getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found")); // Khong thay thi bao loi.
        return toResponse(notification);
    }

    // Cap nhat thong bao theo id.
    public NotificationResponse updateNotification(Long id, NotificationRequest request) {
        Notification notification = notificationRepository.findById(id) // Lay notification cu.
                .orElseThrow(() -> new RuntimeException("Notification not found")); // Khong thay thi bao loi.
        notification.setTitle(request.getTitle());
        notification.setContent(request.getContent());
        notification.setRead(request.getRead());
        notification.setCreatedAt(request.getCreatedAt());
        return toResponse(notificationRepository.save(notification)); // Luu thay doi va tra notification moi.
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getUser().getName(),
                n.getTitle(),
                n.getContent(),
                n.getRead(),
                n.getCreatedAt()
        );
    }
    // Xoa thong bao theo id.
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}

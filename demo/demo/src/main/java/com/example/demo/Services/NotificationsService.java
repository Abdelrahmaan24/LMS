package com.example.demo.Services;

import java.util.List;

import com.example.demo.Models.NotificationType;
import com.example.demo.Models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Notification;
import com.example.demo.Models.User;
import com.example.demo.Repository.NotificationsRepository;

@Service
public class NotificationsService {
    @Autowired
    private NotificationsRepository notificationRepo;

    public void createNotification(User user, String message, NotificationType type) {
        if (user == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }
        System.out.println("User: " + user);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setCreatedAt(java.time.LocalDateTime.now());
        notificationRepo.save(notification);
    }

    public List<Notification> getUserNotifications(User user, boolean unreadOnly) {
        if (unreadOnly) {
            return notificationRepo.findByUserAndRead(user, false);
        } else {
            return notificationRepo.findByUserAndRead(user, true);
        }
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepo.save(notification);
        }
    }
}
package com.example.demo.Controller;


import com.example.demo.Models.Notification;
import com.example.demo.Models.User;
import com.example.demo.Services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {
    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "true") boolean unreadOnly
    ) {
        User user = new User(); // Replace with actual user retrieval logic
        user.setId(userId);
        return notificationsService.getUserNotifications(user, unreadOnly);
    }

    @PutMapping("/mark-as-read/{notificationId}")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationsService.markAsRead(notificationId);
    }
}

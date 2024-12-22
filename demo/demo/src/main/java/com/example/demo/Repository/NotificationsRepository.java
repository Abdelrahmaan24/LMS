package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Notification;
import com.example.demo.Models.User;

public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndRead(User user, boolean read);
}
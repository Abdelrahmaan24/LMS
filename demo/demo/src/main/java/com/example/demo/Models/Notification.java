package com.example.demo.Models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Setter;


@Entity
public class Notification {
    @Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String message;

    @Setter
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Setter
    private boolean read = false;

    @Setter
    private LocalDateTime createdAt;

    @ManyToOne  @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
package com.example.demo.Services;

import com.example.demo.Models.Notification;
import com.example.demo.Models.NotificationType;
import com.example.demo.Models.Student;
import com.example.demo.Models.User;
import com.example.demo.Repository.NotificationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationsServiceTest {

    @Mock
    private NotificationsRepository notificationsRepository;

    @InjectMocks
    private NotificationsService notificationsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
    }

    @Test
    void createNotification_ShouldSaveNotification() {
        // Arrange
        String message = "New Assignment Created";
        NotificationType type = NotificationType.ASSIGNMENT_CREATED;

        // Explicitly create a Student object
        Student testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Youssef Mohamed");

        notificationsService.createNotification(testStudent, message, type);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationsRepository, times(1)).save(captor.capture());
        Notification capturedNotification = captor.getValue();

        assertEquals(testStudent, capturedNotification.getUser());
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(type, capturedNotification.getType());
        assertFalse(capturedNotification.isRead());
        assertNotNull(capturedNotification.getCreatedAt());
    }

    @Test
    void getUserNotifications_ShouldReturnUnreadNotifications() {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUser(testUser);
        notification1.setMessage("Message 1");
        notification1.setRead(false);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUser(testUser);
        notification2.setMessage("Message 2");
        notification2.setRead(false);

        List<Notification> notifications = List.of(notification1, notification2);
        when(notificationsRepository.findByUserAndRead(testUser, false)).thenReturn(notifications);

        // Act
        List<Notification> result = notificationsService.getUserNotifications(testUser, true);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getMessage());
        assertEquals("Message 2", result.get(1).getMessage());
        verify(notificationsRepository, times(1)).findByUserAndRead(testUser, false);
    }

    @Test
    void markAsRead_ShouldUpdateNotificationStatus() {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setRead(false);
        when(notificationsRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Act
        notificationsService.markAsRead(1L);

        // Assert
        assertTrue(notification.isRead());
        verify(notificationsRepository, times(1)).save(notification);
    }

    @Test
    void markAsRead_ShouldDoNothingIfNotificationNotFound() {
        // Arrange
        when(notificationsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        notificationsService.markAsRead(1L);

        // Assert
        verify(notificationsRepository, never()).save(any(Notification.class));
    }
}
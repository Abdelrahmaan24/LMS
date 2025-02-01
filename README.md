# Learning Management System (LMS) - Java-Based Web Application

## Project Overview

The **Learning Management System (LMS)** is a web-based application designed to manage and organize online courses and assessments. It caters to three primary user roles: **Admin**, **Instructor**, and **Student**. The system supports course creation, user management, assessments, performance tracking, and notifications.

---

## Key Features

### 1. **User Management**
- **User Types**: Admin, Instructor, Student.
- **Admin**: Manages system settings, creates users, and oversees courses.
- **Instructor**: Creates and manages courses, uploads course materials, adds assignments and quizzes, grades students, and manages attendance.
- **Student**: Enrolls in courses, accesses course materials, takes quizzes, submits assignments, and views grades.
- **Features**:
  - Role-based user registration and login.
  - Profile management (view/update profile information).

### 2. **Course Management**
- **Course Creation**: Instructors can create courses with details like title, description, and duration. They can also upload media files.
- **Enrollment Management**: Students can view and enroll in available courses. Admins and Instructors can view enrolled students per course.
- **Attendance Management**: Instructors generate OTPs for lessons, and students enter the OTP to mark attendance.

### 3. **Assessment & Grading**
- **Quiz Creation**: Instructors can create quizzes with various question types (MCQ, true/false, short answers).
- **Assignment Submission**: Students can submit assignments by uploading files.
- **Grading and Feedback**: Instructors grade assignments and provide feedback. Students receive automated feedback for quizzes and manual feedback for assignments.

### 4. **Performance Tracking**
- **Student Progress Tracking**: Instructors can track quiz scores, assignment submissions, and attendance.

### 5. **Notifications**
- **System Notifications**: Students and Instructors receive notifications for enrollment confirmations, graded assignments, and course updates.

### 6. **Bonus Features**
- **Role-Based Access Control**: Implemented using Spring Security for authentication and authorization.

---

## Technical Requirements

### Backend
- **Java with Spring Boot** for RESTful API services.
- **PostgreSQL** for database management.

### Integration, Testing & Deployment
- **JUnit** for unit testing.

---

   git clone https://github.com/your-username/lms-project.git

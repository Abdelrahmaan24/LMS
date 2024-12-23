package com.example.demo.Services;


import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Course updateCourse(Long courseId, Course updatedCourse) {
        Optional<Course> existingCourseOpt = courseRepo.findById(courseId);
        if (!existingCourseOpt.isPresent()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        Course existingCourse = existingCourseOpt.get();

        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDuration(updatedCourse.getDuration());
        existingCourse.setMediaFiles(updatedCourse.getMediaFiles());

        return courseRepo.save(existingCourse);
    }


    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID " + id));
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        Admin admin = getAdminById(id);

        admin.setName(adminDetails.getName());
        admin.setEmail(adminDetails.getEmail());
        admin.setPassword(adminDetails.getPassword());
//        admin.setRole(adminDetails.getRole());

        return adminRepo.save(admin);
    }

    public void deleteAdmin(Long id) {
        Admin admin = getAdminById(id);
        adminRepo.delete(admin);
    }

    public Admin createAdmin(Admin admin) {
        if (admin.getRole() == Role.Admin) {
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            return adminRepo.save(admin);
        }
        throw new IllegalArgumentException("Only Admins can be created. Provided role: " + admin.getRole());
    }

    public Student createStudent(Student student) {
        // Check if the student's role is STUDENT
        if (student.getRole() == Role.Student) {
            student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
            return studentRepo.save(student);
        }
        // If the role is not STUDENT, throw an exception
        throw new IllegalArgumentException("Only students can be created. Provided role: " + student.getRole());
    }


    public Instructor createInstructor(Instructor instructor) {
        if (instructor.getRole() == Role.Instructor) {
            instructor.setPassword(bCryptPasswordEncoder.encode(instructor.getPassword()));
            return instructorRepo.save(instructor);
        }
        throw new IllegalArgumentException("Only instructors can be created. Provided role: " + instructor.getRole());
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> courseOpt = courseRepo.findById(courseId);
        if (!courseOpt.isPresent()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
        Course course = courseOpt.get();
        courseRepo.delete(course);
    }
}

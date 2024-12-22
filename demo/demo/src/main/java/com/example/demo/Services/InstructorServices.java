package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServices{

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private StudentRepo studentRepo;

    public Instructor updateInstructor(Instructor instructor, Long id) {
        Optional<Instructor> existingInstructorOpt = instructorRepo.findById(id);

        if (existingInstructorOpt.isPresent()) {
            Instructor existingInstructor = existingInstructorOpt.get();
            existingInstructor.setName(instructor.getName());
            existingInstructor.setEmail(instructor.getEmail());
            existingInstructor.setPassword(instructor.getPassword());
            existingInstructor.setRole(instructor.getRole());

            return instructorRepo.save(existingInstructor);
        } else {
            throw new RuntimeException("Instructor not found with id " + id); // Or you can throw a custom exception
        }
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepo.findAll();
    }

    public Instructor CreateInstructor(Instructor instructor) {
        if (instructor.getCourses() != null) {
            for (Course course : instructor.getCourses()) {
                if (courseRepo.existsByTitle(course.getTitle())){
                    throw new RuntimeException("Course with title '" + course.getTitle() + "' already exists.");
                }
                course.setInstructor(instructor); // Set the instructor reference in the Course
            }
        }
        return instructorRepo.save(instructor);
    }

    public Instructor getInstructorById(Long id) {
//        return instructorRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Instructor with ID " + id + " not found"));
        Optional<Instructor> instructor = instructorRepo.findById(id);
        return instructor.orElse(null);
    }

    public Void DeleteInstructor(Long id) {
        instructorRepo.deleteById(id);
        return null;
    }

    public Course CreateCourse(Long id, Course course) {
        if (courseRepo.existsByTitle(course.getTitle())) {
            throw new RuntimeException("Course with title '" + course.getTitle() + "' already exists.");
        }
        Instructor instructor = getInstructorById(id);
        instructor.addCourse(course); // Add course to instructor
        courseRepo.save(course); // Save the course to the repository
        return course; // Return the created course
    }

    public Course updateCourse(Long instructorId, Long courseId, Course updatedCourse) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor == null) {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        Optional<Course> existingCourseOpt = courseRepo.findById(courseId);
        if (!existingCourseOpt.isPresent()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        Course existingCourse = existingCourseOpt.get();

        if (!existingCourse.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Course does not belong to the specified instructor.");
        }
        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDuration(updatedCourse.getDuration());
        existingCourse.setMediaFiles(updatedCourse.getMediaFiles());

        return courseRepo.save(existingCourse);
    }

    public void deleteCourse(Long instructorId, Long courseId) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor == null) {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        Optional<Course> courseOpt = courseRepo.findById(courseId);
        if (!courseOpt.isPresent()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        Course course = courseOpt.get();

        // Ensure the course belongs to the instructor
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Course does not belong to the specified instructor.");
        }

        courseRepo.delete(course);
    }



    public Lesson addLessonToCourse(Long instructorId, Long courseId, Lesson lesson) {
        // Fetch the instructor and verify they exist
        Instructor instructor = getInstructorById(instructorId);
        if (instructor == null) {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        // Fetch the course and verify it belongs to the instructor
        Course course = instructor.getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        // Associate the lesson with the course
        lesson.setCourse(course);

        course.addLesson(lesson);

        // Save the lesson
        return lessonRepo.save(lesson);
    }


    // Method to remove a student from a course, with instructor validation
    public void removeStudentFromCourse(Long instructorId, Long studentId, Long courseId) {
        Optional<Course> courseOpt = courseRepo.findById(courseId);
        Optional<Instructor> instructorOpt = instructorRepo.findById(instructorId);
        Optional<Student> studentOpt = studentRepo.findById(studentId);

        if (!courseOpt.isPresent()) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        if (!instructorOpt.isPresent()) {
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        if (!studentOpt.isPresent()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        Course course = courseOpt.get();
        Instructor instructor = instructorOpt.get();
        Student student = studentOpt.get();

        // Ensure the instructor is the one assigned to the course
        if (!course.getInstructor().getId().equals(instructor.getId())) {
            throw new RuntimeException("Instructor not authorized to remove students from this course.");
        }

        // Find the enrollment associated with the student and course
        Enrollment enrollment = enrollmentRepo.findByStudentAndCourse(student, course);

        if (enrollment != null) {
            // Remove the enrollment
            enrollmentRepo.delete(enrollment);
        } else {
            throw new RuntimeException("Enrollment not found for student and course");
        }
    }
}
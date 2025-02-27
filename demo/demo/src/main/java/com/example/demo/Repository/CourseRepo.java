package com.example.demo.Repository;

import com.example.demo.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);

}

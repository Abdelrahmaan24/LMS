package com.example.demo.Repository;

import com.example.demo.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
 List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByLessonId(Long lessonId);
}

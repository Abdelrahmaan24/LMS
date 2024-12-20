package com.example.demo.Repository;

import com.example.demo.Models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
}

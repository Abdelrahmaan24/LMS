package com.example.demo.Repository;


import com.example.demo.Models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // You can define custom queries here if needed
}

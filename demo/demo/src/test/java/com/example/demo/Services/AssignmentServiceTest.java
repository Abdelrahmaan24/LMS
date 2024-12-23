package com.example.demo.Services;

import com.example.demo.Models.Assignment;
import com.example.demo.Repository.AssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreateAssignment() {
//        Assignment assignment = new Assignment();
//        assignment.setTitle("Math Assignment");
//
//        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);
//
//        Assignment createdAssignment = assignmentService.createAssignment(assignment);
//
//        assertNotNull(createdAssignment);
//        assertEquals("Math Assignment", createdAssignment.getTitle());
//
//        verify(assignmentRepository, times(1)).save(assignment);
//    }
}

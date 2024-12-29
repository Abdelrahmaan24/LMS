package com.example.demo.Controller;

import com.example.demo.Dto.*;
import com.example.demo.Models.*;
import com.example.demo.Services.AssignmentService;
import com.example.demo.Services.CourseServices;
import com.example.demo.Services.InstructorServices;
import com.example.demo.Services.QuizService;
import com.example.demo.Services.OptionService;
import com.example.demo.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/Instructor")
public class InstructorController {

    @Autowired
    private InstructorServices instructorServices;

    @Autowired
    private CourseServices courseServices;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private QuizService quizService;


    // get all Instructors
    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors(){
        return ResponseEntity.ok(instructorServices.getAllInstructors());
    }


    // create Instructors
    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor){
        return ResponseEntity.ok(instructorServices.CreateInstructor(instructor));
    }

    // get Instructors by Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable("id") Long id) {
        Instructor instructor = instructorServices.getInstructorById(id);
        if (instructor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructor);
    }


    // Update Instructor
    @PutMapping(value = "/{id}")
    public ResponseEntity<Instructor> updateInstructor(@RequestBody InstructorDto instructorDto, @PathVariable Long id){
        Instructor instructor = new Instructor();
        instructor.setName(instructorDto.getName());
        instructor.setEmail(instructorDto.getEmail());
        instructor.setPassword(instructorDto.getPassword());
        instructor.setRole(instructorDto.getRole());
        return ResponseEntity.ok(instructorServices.updateInstructor(instructor, id));
    }


    // Delete Student
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id){
        Instructor instructor = instructorServices.getInstructorById(id);
        if (instructor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructorServices.DeleteInstructor(id));
    }


    // Create course
    @PostMapping(value = "/{id}/CreateCourse")
    public ResponseEntity<Course> createCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setMediaFiles(courseDto.getMediaFiles());
        course.setLessons(new ArrayList<Lesson>());
        course.setInstructor(instructorServices.getInstructorById(id));
        Course createdCourse = instructorServices.CreateCourse(id, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }


    // Update Course
    @PutMapping(value = "/{instructorId}/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long instructorId, @PathVariable Long courseId, @RequestBody CourseDto courseDto) {
        Course updatedCourse = new Course();
        updatedCourse.setTitle(courseDto.getTitle());
        updatedCourse.setDescription(courseDto.getDescription());
        updatedCourse.setDuration(courseDto.getDuration());
        updatedCourse.setMediaFiles(courseDto.getMediaFiles());
        updatedCourse.setLessons(new ArrayList<Lesson>());
        updatedCourse.setInstructor(instructorServices.getInstructorById(instructorId));
        Course course = instructorServices.updateCourse(instructorId, courseId, updatedCourse);
        return ResponseEntity.ok(course);
    }


    // Delete Course
    @DeleteMapping(value = "/{instructorId}/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long instructorId, @PathVariable Long courseId) {
        instructorServices.deleteCourse(instructorId, courseId);
        return ResponseEntity.noContent().build();
    }


    // Add a lesson to a course
    @PostMapping(value = "/{instructorId}/courses/{courseId}/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @RequestBody LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setOtp(lessonDto.getOtp());
        lesson.setCourse(courseServices.getCourseById(courseId));
        lesson.setOtpStartTime(lessonDto.getOtpStartTime());
        Lesson createdLesson = instructorServices.addLessonToCourse(instructorId, courseId, lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
    }



    // get all students enrolled in course
    @GetMapping(value = "/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<Student> students = courseServices.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }


    // Remove student from course (with instructor validation)
    @DeleteMapping(value = "/{instructorId}/courses/{courseId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @PathVariable Long studentId) {

        try {
            instructorServices.removeStudentFromCourse(instructorId, studentId, courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Forbidden if the instructor is not authorized
        }
    }


    // Create a new assignment
    @PostMapping(value = "/{Instructorid}/course/{Courseid}/assignmnet")
    public ResponseEntity<Assignment> createAssignment(@PathVariable Long Instructorid, @PathVariable Long Courseid, @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = new Assignment();
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDueDate(assignmentDto.getDueDate());
        assignment.setCourse(courseServices.getCourseById(Courseid));
        Assignment createdAssignment = assignmentService.createAssignment(assignment, Courseid);
        return ResponseEntity.ok(createdAssignment);
    }

    // Create a new quiz
    @PostMapping(value = "/{InstructorID}/courses/{courseId}/Quiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto, @PathVariable Long InstructorID, @PathVariable Long courseId) {
        // Fetch the course by courseId from the database
        Course course = courseServices.getCourseById(courseId);

        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return error if the course is not found
        }

        // Create the quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setCourse(course);  // Set the course object

        // Save the quiz (assuming your quizService creates and saves the quiz)
        Quiz createdQuiz = quizService.createQuiz(quiz);

        return ResponseEntity.ok(createdQuiz);
    }
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionService optionService;

    @PostMapping("/quiz/{quizId}/question")
    public ResponseEntity<Question> createQuestion(
            @PathVariable Long quizId,
            @RequestBody QuestionDto questionDto) {

        // Fetch the quiz by quizId
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Quiz not found
        }

        // Create the question
        Question question = new Question();
        question.setQuestionText(questionDto.getQuestionText());
        question.setQuiz(quiz); // Set the quiz for the question

        // Save the question first
        Question createdQuestion = questionService.createQuestion(question); // Assuming this saves the question

        // Now create the options
        List<Option> options = new ArrayList<>();
        for (OptionDto optionDto : questionDto.getOptions()) {
            Option option = new Option();
            option.setText(optionDto.getText());
            option.setIsCorrect(optionDto.getIsCorrect());
            option.setQuestion(createdQuestion);  // Set the saved question for each option
            options.add(option);
        }

        // Save the options after the question is persisted
        optionService.saveOptions(options); // Now this will work, as saveOptions is defined in OptionService

        // Return the created question along with its options
        createdQuestion.setOptions(options);  // Optionally, return the options as part of the question response
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }
}


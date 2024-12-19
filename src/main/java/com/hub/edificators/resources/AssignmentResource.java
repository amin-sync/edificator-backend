package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.assignment.CreateAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.GradeAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.ListStudentAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.SubmitAssignmentRequest;
import com.hub.edificators.services.AssignmentService;
import com.hub.edificators.validators.AssignmentResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/assignment")
public class AssignmentResource {

    @Autowired
    private AssignmentResourceValidator assignmentResourceValidator;

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/create")
    public ResponseEntity createAssignment(@RequestParam String title,
                                           @RequestParam String dueDate,
                                           @RequestParam String courseId,
                                           @RequestParam("file") MultipartFile file) {
        CreateAssignmentRequest request = CreateAssignmentRequest.builder().title(title).dueDate(dueDate).courseId(courseId).build();
        assignmentResourceValidator.validate(request);
        return ResponseEntity.ok(assignmentService.createAssignment(request, file));
    }

    @PostMapping("/submit")
    public ResponseEntity submitAssignment(@RequestParam String assignmentId,
                                           @RequestParam String studentId, @RequestParam("file") MultipartFile file) {
        SubmitAssignmentRequest request = SubmitAssignmentRequest.builder().assignmentId(Long.parseLong(assignmentId)).studentId(Long.parseLong(studentId)).build();
        assignmentResourceValidator.validate(request);
        return ResponseEntity.ok(assignmentService.submitAssignment(request, file));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteAssignment(@RequestParam Long assignmentId) {
        assignmentResourceValidator.validate("Assignment ID", assignmentId.toString());
        return ResponseEntity.ok(assignmentService.deleteAssignment(assignmentId));
    }

    @GetMapping("/list-by-teacher")
    public ResponseEntity listCourseAssignment(@RequestParam Long courseId) {
        assignmentResourceValidator.validate("Course ID", courseId.toString());
        return ResponseEntity.ok(assignmentService.listCourseAssignment(courseId));
    }

    @GetMapping("status-list")
    public ResponseEntity AssignmentstatusList(@RequestParam Long assignmentId) {
        assignmentResourceValidator.validate("Assignment id", assignmentId.toString());
        return ResponseEntity.ok(assignmentService.assignmentStatusList(assignmentId));
    }

    @PostMapping("/grade")
    public ResponseEntity assignmentGrade(@RequestBody GradeAssignmentRequest request) {
        assignmentResourceValidator.validate(request);
        return ResponseEntity.ok(assignmentService.assignmentGrade(request));
    }

    @PostMapping("/list-by-student")
    public ResponseEntity listStudentAssignment(@RequestBody ListStudentAssignmentRequest request) {
        assignmentResourceValidator.validate(request);
        return ResponseEntity.ok(assignmentService.listStudentAssignment(request));
    }

}

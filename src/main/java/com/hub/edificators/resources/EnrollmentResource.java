package com.hub.edificators.resources;

import com.hub.edificators.services.EnrollmentService;
import com.hub.edificators.validators.EnrollmentResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentResource {

    @Autowired
    private EnrollmentResourceValidator enrollmentResourceValidator;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/list-student")
    public ResponseEntity listStudent(@RequestParam Long studentId){
        enrollmentResourceValidator.validate("Student ID",studentId.toString());
        return ResponseEntity.ok(enrollmentService.listEnrolledCourses(studentId));
    }
}

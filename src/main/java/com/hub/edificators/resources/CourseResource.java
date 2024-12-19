package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.course.CreateCourseRequest;
import com.hub.edificators.dtos.requests.course.UpdateCourseRequest;
import com.hub.edificators.services.CourseService;
import com.hub.edificators.validators.CourseResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course")
public class CourseResource {

    @Autowired
    private CourseResourceValidator courseResourceValidator;

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity createCourse(@RequestBody CreateCourseRequest request){
        courseResourceValidator.validate(request);
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @PutMapping("/update")
    public ResponseEntity updateCourse(@RequestBody UpdateCourseRequest request){
        courseResourceValidator.validate(request);
        return ResponseEntity.ok(courseService.updateCourse(request));
    }

    @GetMapping ("/list-teacher")
    public ResponseEntity getTeacherCourseList(@RequestParam Long teacherId){
        courseResourceValidator.validate("Teacher ID", teacherId.toString());
        return ResponseEntity.ok(courseService.listTeacherCourse(teacherId));
    }

    @GetMapping("/details")
    public ResponseEntity getDetails(@RequestParam Long courseId){
        courseResourceValidator.validate("Course ID",courseId.toString());
        return ResponseEntity.ok(courseService.courseDetails(courseId));
    }
    @GetMapping ("/list-by-grade")
    public ResponseEntity getGradeCourseList(@RequestParam Long studentId){
        courseResourceValidator.validate("Student ID", studentId.toString());
        return  ResponseEntity.ok(courseService.listGradeCourse(studentId));
    }
}

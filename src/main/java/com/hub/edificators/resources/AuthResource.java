package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.user.RegisterStudentRequest;
import com.hub.edificators.dtos.requests.user.RegisterTeacherRequest;
import com.hub.edificators.dtos.requests.user.LoginRequest;
import com.hub.edificators.services.UserService;
import com.hub.edificators.validators.AuthResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthResourceValidator authResourceValidator;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request){
        authResourceValidator.validate(request);
        return ResponseEntity.ok(userService.userLogin(request));
    }

    @PostMapping("/register-teacher")
    public ResponseEntity registerTeacher(@RequestBody RegisterTeacherRequest request){
        authResourceValidator.validate(request);
        return ResponseEntity.ok(userService.registerTeacher(request));
    }

    @PostMapping("/register-student")
    public ResponseEntity registerStudent(@RequestBody RegisterStudentRequest request){
        authResourceValidator.validate(request);
        return ResponseEntity.ok(userService.registerStudent(request));
    }

}

package com.hub.edificators.resources;

import com.hub.edificators.constants.AppConstants;
import com.hub.edificators.dtos.requests.user.UpdatePasswordRequest;
import com.hub.edificators.services.UserService;
import com.hub.edificators.utils.FileUtility;
import com.hub.edificators.validators.UserResourceValidator;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private UserResourceValidator userResourceValidator;

    @GetMapping("/get-profile")
    public ResponseEntity getProfile(@RequestParam Long userId) {
        userResourceValidator.validate("user id", userId.toString());
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/update-password")
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordRequest request) {
        userResourceValidator.validate(request);
        return ResponseEntity.ok(userService.updatePassword(request));
    }

    @PostMapping("/upload-profile-pic")
    public ResponseEntity uploadProfilePic(@RequestParam Long userId, @RequestParam("file") MultipartFile file) {
        userResourceValidator.validate("user id", userId.toString());
        return ResponseEntity.ok(userService.uploadProfilePic(userId, file));
    }

    @GetMapping("/list-by-student")
    public ResponseEntity listTeachersByStudentId(@RequestParam Long studentId) {
        userResourceValidator.validate("Student id", studentId.toString());
        return ResponseEntity.ok(userService.listTeachersByStudentId(studentId));
    }

    @GetMapping("/list-by-teacher")
    public ResponseEntity listStudentsByTeacherId(@RequestParam Long teacherId) {
        userResourceValidator.validate("Teacher id", teacherId.toString());
        return ResponseEntity.ok(userService.listStudentsByTeacherId(teacherId));
    }

}

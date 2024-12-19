package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.user.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    GenericResponse getProfile(Long userId);

    GenericResponse updatePassword(UpdatePasswordRequest request);

    GenericResponse registerTeacher(RegisterTeacherRequest request);

    GenericResponse registerStudent(RegisterStudentRequest request);

    GenericResponse userLogin(LoginRequest request);

    GenericResponse uploadProfilePic(Long userId, MultipartFile file);

    GenericResponse listStudentsByTeacherId(Long teacherId);

    GenericResponse listTeachersByStudentId(Long studentId);
}

package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.enrollment.CreateEnrollmentRequest;

public interface EnrollmentService {
    GenericResponse createEnrollment(CreateEnrollmentRequest request);
    GenericResponse listEnrolledCourses(Long studentId);
}

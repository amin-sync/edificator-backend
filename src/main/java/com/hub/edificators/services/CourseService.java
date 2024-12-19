package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.course.CreateCourseRequest;
import com.hub.edificators.dtos.requests.course.UpdateCourseRequest;

public interface CourseService {
    GenericResponse createCourse(CreateCourseRequest request);
    GenericResponse updateCourse(UpdateCourseRequest request);
    GenericResponse listTeacherCourse(Long teacherId);
    GenericResponse courseDetails(Long courseId);
    GenericResponse listGradeCourse(Long studentId);
}

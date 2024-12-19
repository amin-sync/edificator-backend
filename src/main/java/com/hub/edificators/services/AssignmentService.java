package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.assignment.GradeAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.CreateAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.ListStudentAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.SubmitAssignmentRequest;
import org.springframework.web.multipart.MultipartFile;

public interface AssignmentService {
    GenericResponse createAssignment(CreateAssignmentRequest request, MultipartFile file);
    GenericResponse deleteAssignment(Long assignmentId);
    GenericResponse listCourseAssignment(long courseId);
    GenericResponse assignmentStatusList(long assignmentId);
    GenericResponse assignmentGrade(GradeAssignmentRequest request);
    GenericResponse listStudentAssignment(ListStudentAssignmentRequest request);
    GenericResponse submitAssignment(SubmitAssignmentRequest assignmentId, MultipartFile file);

}

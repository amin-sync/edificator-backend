package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.assignment.GradeAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.CreateAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.ListStudentAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.SubmitAssignmentRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AssignmentResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(CreateAssignmentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getTitle())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Title is missing");
        }
        if (StringUtils.isBlank(request.getDueDate().toString())){
            errors.append(errors.isEmpty() ? "" : ", ").append("Due Date is missing");
        }
        if (StringUtils.isBlank((request.getCourseId()))){
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is missing");
        }


        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }


    public void validate(GradeAssignmentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (request.getAssignmentId()==0) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Assignment ID is missing");
        }
        if (request.getStudentId()==0){
            errors.append(errors.isEmpty() ? "" : ", ").append("Student ID is missing");
        }
        if (StringUtils.isBlank(request.getMarks())){
            errors.append(errors.isEmpty() ? "" : ", ").append("Invalid Grade Value!");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }


    public void validate(SubmitAssignmentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if ((request.getAssignmentId() == 0)) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Assignment ID is missing");
        }

        if ((request.getStudentId() == 0)) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Student ID is missing");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validate(ListStudentAssignmentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (request.getCourseId()==0) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is missing");
        }
        if (request.getStudentId()==0){
            errors.append(errors.isEmpty() ? "" : ", ").append("Student ID is missing");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }
}

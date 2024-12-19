package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.course.CreateCourseRequest;
import com.hub.edificators.dtos.requests.course.UpdateCourseRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CourseResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(CreateCourseRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getGrade())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Grade is missing");
        }
        if (StringUtils.isBlank(request.getSubject())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Subject is missing");
        }
        if (StringUtils.isBlank(request.getDays())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Days are missing");
        }
        if (request.getFromTime()==null) {
            errors.append(errors.isEmpty() ? "" : ", ").append("From Time is missing");
        }
        if (request.getToTime()==null) {
            errors.append(errors.isEmpty() ? "" : ", ").append("To Time is missing");
        }
        if (StringUtils.isBlank(request.getFee())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Fee missing");
        }
        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validate(UpdateCourseRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (request.getCourseId()==0) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is Invalid");
        }
        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }
}

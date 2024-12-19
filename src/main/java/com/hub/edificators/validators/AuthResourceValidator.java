package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.user.RegisterStudentRequest;
import com.hub.edificators.dtos.requests.user.RegisterTeacherRequest;
import com.hub.edificators.dtos.requests.user.LoginRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for teacher dto
    public void validate(RegisterTeacherRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getEmail())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Email is missing");
        }
        if (StringUtils.isBlank(request.getFullName())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Name is missing");
        }
        if (StringUtils.isBlank(request.getNic())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("NIC is missing");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("password is missing");
        }
        if (StringUtils.isBlank(request.getExpertise())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("expertise is missing");
        }
        if (request.getRoleId()==1){
            errors.append(errors.isEmpty()? "" : ",").append("User can't be Admin.");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    // for student dto
    public void validate(RegisterStudentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getEmail())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Email is missing");
        }
        if (StringUtils.isBlank(request.getFullName())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Name is missing");
        }
        if (StringUtils.isBlank(request.getNic())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("NIC is missing");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("password is missing");
        }
        if (StringUtils.isBlank(request.getGrade())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Grade is missing");
        }
        if (request.getRoleId()==1){
            errors.append(errors.isEmpty()? "" : ",").append("User can't be Admin.");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validate(LoginRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!", HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getEmail())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Email is missing");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Password is missing");
        }
    }


}

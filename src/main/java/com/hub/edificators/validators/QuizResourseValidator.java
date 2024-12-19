package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.assignment.ListStudentAssignmentRequest;
import com.hub.edificators.dtos.requests.payment.ListStudentQuizRequest;
import com.hub.edificators.dtos.requests.quiz.CreateQuizRequest;
import com.hub.edificators.dtos.requests.quiz.QuizSubmitRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class QuizResourseValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(CreateQuizRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!", HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getTitle())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Title is missing");
        }

        if (StringUtils.isBlank(request.getDuration())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Duration is missing");
        }

        if (request.getCourseId()==null) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is missing");
        }

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Questions are missing");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validate(ListStudentQuizRequest request) throws BusinessException {
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

    public void validate(QuizSubmitRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (request.getQuizId()==0) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Quiz ID is missing");
        }
        if (request.getStudentId()==0){
            errors.append(errors.isEmpty() ? "" : ", ").append("Student ID is missing");
        }
        if (request.getQuestionAnwerList()==null){
            errors.append(errors.isEmpty() ? "" : ", ").append("List should not be null");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

}

package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.enrollment.UnenrollRequest;
import com.hub.edificators.dtos.requests.payment.CreatePaymentRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(CreatePaymentRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!", HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getNameOnCard())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Name is missing");
        }

        if (StringUtils.isBlank(request.getCardNumber())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Card Number is missing");
        }

        if (StringUtils.isBlank(request.getCvc()) || request.getCvc().length() > 3) {
            errors.append(errors.isEmpty() ? "" : ", ").append("CVC is missing");
        }

        if (StringUtils.isBlank(request.getExpiryMonth())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Month is missing");
        }

        if (StringUtils.isBlank(request.getExpiryYear())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Year is missing");
        }

        if ((request.getCourseId() == 0)) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is missing");
        }

        if ((request.getStudentId() == 0)) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Student ID is missing");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validate(UnenrollRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!", HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder error = new StringBuilder();

        if ((request.getCourseId() == 0)) {
            error.append(error.isEmpty() ? "" : ", ").append("Course ID is missing");
        }

        if ((request.getStudentId() == 0)) {
            error.append(error.isEmpty() ? "" : ", ").append("Course ID is missing");
        }
        if (!error.isEmpty()) {
            throw new BusinessException(String.valueOf(error), HttpStatus.BAD_REQUEST.value());
        }
    }
}

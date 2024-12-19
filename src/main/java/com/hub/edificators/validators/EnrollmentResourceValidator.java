package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }
}

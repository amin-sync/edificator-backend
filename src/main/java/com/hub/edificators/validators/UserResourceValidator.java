package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.user.UpdatePasswordRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserResourceValidator {

    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(UpdatePasswordRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!", HttpStatus.BAD_REQUEST.value());
        }
        StringBuilder errors = new StringBuilder();
        if (request.getUserId()==0) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Id is invalid");
        }
        if (StringUtils.isBlank(request.getOldPassword())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Current password is missing");
        }
        if (StringUtils.isBlank(request.getNewPassword())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("New password is missing");
        }
        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }
}

package com.hub.edificators.validators;

import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.announcement.CreateAnnouncementRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementResourceValidator {
    // for param
    public void validate(String requestParam, String value) throws BusinessException {
        if (value == null || StringUtils.isBlank(value)) {
            throw new BusinessException(requestParam + "is invalid", HttpStatus.BAD_REQUEST.value());
        }
    }

    // for dto
    public void validate(CreateAnnouncementRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException("Request Can't be null!",HttpStatus.BAD_REQUEST.value());
        }

        StringBuilder errors = new StringBuilder();
        if (StringUtils.isBlank(request.getMessage())) {
            errors.append(errors.isEmpty() ? "" : ", ").append("Message is missing");
        }
        if ((request.getCourseId()==0)){
            errors.append(errors.isEmpty() ? "" : ", ").append("Course ID is missing");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.valueOf(errors), HttpStatus.BAD_REQUEST.value());
        }
    }
}

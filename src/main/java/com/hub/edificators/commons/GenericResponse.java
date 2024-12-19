package com.hub.edificators.commons;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    private boolean success;
    private String successMessage;
    private String errorMessage;
    private int status;
    private Object data;

    public static GenericResponse createSuccessResponse(String successMessage, Object data, int status) {
        return GenericResponse.builder().data(data).success(true).successMessage(successMessage).errorMessage(null).status(status).build();
    }

    public static GenericResponse createErrorResponse(String errorMessage, Object data, int status) {
        return GenericResponse.builder().success(false).successMessage(null).errorMessage(errorMessage).status(status).data(data).build();
    }

}

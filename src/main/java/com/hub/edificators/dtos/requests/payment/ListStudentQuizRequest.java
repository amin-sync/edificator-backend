package com.hub.edificators.dtos.requests.payment;

import lombok.Data;

@Data
public class ListStudentQuizRequest {
    private long studentId;
    private long courseId;
}

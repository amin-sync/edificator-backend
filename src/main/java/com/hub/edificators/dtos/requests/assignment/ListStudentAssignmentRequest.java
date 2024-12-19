package com.hub.edificators.dtos.requests.assignment;

import lombok.Data;

@Data
public class ListStudentAssignmentRequest {
    private long studentId;
    private long courseId;
}

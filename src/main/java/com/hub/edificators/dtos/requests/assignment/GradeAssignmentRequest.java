package com.hub.edificators.dtos.requests.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GradeAssignmentRequest {
    private long assignmentId;
    private long studentId;
    private String marks;
}

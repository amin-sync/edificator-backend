package com.hub.edificators.dtos.requests.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubmitAssignmentRequest {
    private long assignmentId;
    private long studentId;
}

package com.hub.edificators.dtos.requests.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateAssignmentRequest {
    private String title;
    private String dueDate;
    private String courseId;
}

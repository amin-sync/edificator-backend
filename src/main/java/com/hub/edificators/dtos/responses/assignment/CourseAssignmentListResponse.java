package com.hub.edificators.dtos.responses.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseAssignmentListResponse {
    private long id;
    private String title;
    private String dueDate;
}

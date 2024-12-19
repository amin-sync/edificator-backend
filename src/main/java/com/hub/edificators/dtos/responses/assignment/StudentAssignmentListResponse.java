package com.hub.edificators.dtos.responses.assignment;

import lombok.Data;

import java.util.Date;

@Data
public class StudentAssignmentListResponse {
    private long id;
    private String title;
    private String dueDate;
    private String status;
    private String fileName;
    private String marks;
}

package com.hub.edificators.dtos.responses.dashboard;

import lombok.Data;

@Data
public class StudentDashboardResponse {
    private long AssignmentCount;
    private long QuizCount;
    private String fullName;
}

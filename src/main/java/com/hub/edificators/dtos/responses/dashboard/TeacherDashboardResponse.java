package com.hub.edificators.dtos.responses.dashboard;

import lombok.Data;

@Data
public class TeacherDashboardResponse {
    private long courseCount;
    private long enrolledStudentCount;
    private String fullName;
}

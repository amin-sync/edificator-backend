package com.hub.edificators.dtos.responses.course;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherCourseListResponse {
    private long id;
    private String subject;
    private String grade;
    private String fee;
    private String days;
    private String fromTime;
    private String toTime;
}

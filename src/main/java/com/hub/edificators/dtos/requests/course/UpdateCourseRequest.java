package com.hub.edificators.dtos.requests.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCourseRequest {
    private long courseId;
    private String grade;
    private String subject;
    private String days;
    private String fromTime;
    private String toTime;
    private String fee;
}

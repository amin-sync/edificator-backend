package com.hub.edificators.dtos.requests.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCourseRequest {
    private String grade;
    private String subject;
    private String days;
    private String fromTime;
    private String toTime;
    private String fee;
    private long teacherId;
}

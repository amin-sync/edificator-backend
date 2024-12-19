package com.hub.edificators.dtos.responses.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDetailsResponse {

    private String fullName;
    private String profileUrl;
    private String Expertise;
    private String subject;
    private String grade;
    private String days;
    private String fromTime;
    private String toTime;
    private String fee;
}

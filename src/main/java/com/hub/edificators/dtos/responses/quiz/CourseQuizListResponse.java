package com.hub.edificators.dtos.responses.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class CourseQuizListResponse {
    private long id;
    private String title;
    private String duration;
    private String duedate;
}

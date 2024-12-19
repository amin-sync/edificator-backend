package com.hub.edificators.dtos.responses.quiz;

import lombok.Data;

import java.util.Date;

@Data
public class StudentQuizListResponse {
    private long id;
    private String title;
    private String duration;
    private String status;
    private String marks;
    private String outOf;
}

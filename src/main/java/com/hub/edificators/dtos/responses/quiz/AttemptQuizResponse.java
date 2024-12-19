package com.hub.edificators.dtos.responses.quiz;

import lombok.Data;

@Data
public class AttemptQuizResponse {
    private String question;
    private long questionId;
    private String answer1;
    private long answer1Id;
    private String answer2;
    private long answer2Id;
    private String answer3;
    private long answer3Id;
    private String answer4;
    private long answer4Id;
    private String duration;
}

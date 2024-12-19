package com.hub.edificators.dtos.responses.quiz;

import lombok.Data;

@Data
public class QuizResultResponse {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private double score;
}


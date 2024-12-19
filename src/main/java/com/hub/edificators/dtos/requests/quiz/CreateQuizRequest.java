package com.hub.edificators.dtos.requests.quiz;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateQuizRequest {
    private String title;
    private String duration;
    private int noOfQuestions;
    private Long courseId;
    private List<QuizQuestionRequest> questions;

    @Data
    public static class QuizQuestionRequest{
        private String text;
        private List<QuizAnswerRequest> answers;
    }

    @Data
    public static class QuizAnswerRequest {
        private String answer;
        private boolean isCorrect;
    }
}



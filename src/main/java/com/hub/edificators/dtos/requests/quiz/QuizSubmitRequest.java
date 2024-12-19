package com.hub.edificators.dtos.requests.quiz;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequest {
    private long quizId;
    private long studentId;
    private List<QuestionAnswer> questionAnwerList;

    @Data
    public static class QuestionAnswer{
        private long questionId;
        private long answerId;
    }
}

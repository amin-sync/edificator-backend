package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.payment.ListStudentQuizRequest;
import com.hub.edificators.dtos.requests.quiz.CreateQuizRequest;
import com.hub.edificators.dtos.requests.quiz.QuizSubmitRequest;

public interface QuizService {
    GenericResponse createQuiz(CreateQuizRequest request);
//    QuizResultResponse attemptQuiz(QuizAttemptRequest request);
    GenericResponse listCourseQuiz(Long courseId);
    GenericResponse quizStatusList(long quizId);
    GenericResponse listStudentQuiz(ListStudentQuizRequest request);
    GenericResponse quizAttempt(long quizId);
    GenericResponse quizSubmit(QuizSubmitRequest request);
}

package com.hub.edificators.repos;

import com.hub.edificators.entities.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepo extends JpaRepository<QuizAnswer,Long> {

    List<QuizAnswer> findByQuizQuestionId(long quizQuestionId);
}

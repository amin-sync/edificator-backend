package com.hub.edificators.repos;

import com.hub.edificators.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepo extends JpaRepository<QuizQuestion,Long> {
    List<QuizQuestion> findByQuizId(long quizId);
}

package com.hub.edificators.repos;

import com.hub.edificators.entities.QuizSubmission;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.enums.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizSubmissionRepo extends JpaRepository<QuizSubmission,Long> {
    List<QuizSubmission> findByStudentIdAndFlagAndStatus(long studentId, Flag flag, QuizStatus status);
    Optional<QuizSubmission> findByStudentIdAndQuizId(long studentId, long quizId);
//    QuizSubmission findByStudentIdAndQuizId(long studentId, long quizId);

}

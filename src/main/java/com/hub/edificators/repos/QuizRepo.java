package com.hub.edificators.repos;

import com.hub.edificators.entities.Quiz;
import com.hub.edificators.enums.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepo extends JpaRepository<Quiz,Long> {
    List<Quiz> findByCourseId(long courseId);
    List<Quiz> findByCourseIdAndFlag(Long courseId, Flag flag);

    List<Quiz> findByCourseIdInAndFlag(List<Long> courseIds, Flag flag);
}

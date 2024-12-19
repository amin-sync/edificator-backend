package com.hub.edificators.repos;

import com.hub.edificators.entities.Assignment;
import com.hub.edificators.enums.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourseIdAndFlag(Long courseId, Flag flag);
    List<Assignment> findByCourseIdInAndFlag(List<Long> courseIds, Flag flag);
}

package com.hub.edificators.repos;

import com.hub.edificators.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    Teacher findByUserId(Long userId);
}

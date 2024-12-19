package com.hub.edificators.repos;

import com.hub.edificators.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findByTeacherId(Long teacherId);
    List<Course> findByGrade(String grade);

}

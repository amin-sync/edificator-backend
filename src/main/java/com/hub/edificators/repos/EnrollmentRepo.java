package com.hub.edificators.repos;

import com.hub.edificators.entities.Enrollment;
import com.hub.edificators.enums.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment,Long> {
    Enrollment findByCourseIdAndStudentId(Long courseId, Long studentId);
    List<Enrollment> findByStudentIdAndFlag(Long studentId, Flag flag);
    List<Enrollment> findByCourseIdAndFlag(Long courseId,Flag flag);
    // EnrollmentRepo.java
    @Query("SELECT e FROM Enrollment e WHERE e.course.id IN :courseIds AND e.flag = :flag")
    List<Enrollment> findByCourseIdsAndFlag(@Param("courseIds") List<Long> courseIds, @Param("flag") Flag flag);

}

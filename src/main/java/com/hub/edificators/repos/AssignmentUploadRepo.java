package com.hub.edificators.repos;

import com.hub.edificators.entities.Assignment;
import com.hub.edificators.entities.AssignmentUpload;
import com.hub.edificators.entities.Quiz;
import com.hub.edificators.enums.AssignmentStatus;
import com.hub.edificators.enums.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentUploadRepo extends JpaRepository<AssignmentUpload,Long> {
    List<AssignmentUpload> findByStudentIdAndFlagAndStatus(long studentId, Flag flag, AssignmentStatus status);
    Optional<AssignmentUpload> findByStudentId(long studentId);
    AssignmentUpload findByAssignmentId(long assignmentId);
    AssignmentUpload findByStudentIdAndAssignmentId(long studentId,long assignmentId);

    @Query("SELECT au FROM AssignmentUpload au " +
            "JOIN au.assignment a " +
            "WHERE au.student.id = :studentId " +
            "AND au.flag = :flag " +
            "AND au.status IN :statuses " +
            "AND a.course.id IN :courseIds")
    List<AssignmentUpload> findByStudentIdAndFlagAndStatusAndCourseIds(
            @Param("studentId") long studentId,
            @Param("flag") Flag flag,
            @Param("statuses") List<AssignmentStatus> status,
            @Param("courseIds") List<Long> courseIds);
}

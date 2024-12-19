package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.responses.dashboard.StudentDashboardResponse;
import com.hub.edificators.dtos.responses.dashboard.TeacherDashboardResponse;
import com.hub.edificators.entities.*;
import com.hub.edificators.enums.AssignmentStatus;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.enums.QuizStatus;
import com.hub.edificators.repos.*;
import com.hub.edificators.services.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private AssignmentUploadRepo assignmentUploadRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuizSubmissionRepo quizSubmissionRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public GenericResponse teacherDashboard(long teacherId) {
        try {
            logger.info("teacherDashboard() -> request received.");
            List<Course> course = courseRepo.findByTeacherId(teacherId);
            Teacher teacher = teacherRepo.getReferenceById(teacherId);
            List<Long> courseIds = course.stream()
                    .map(Course::getId)
                    .collect(Collectors.toList());
            List<Enrollment> enrollmentList = enrollmentRepo.findByCourseIdsAndFlag(courseIds, Flag.ACTIVE);

            TeacherDashboardResponse response = new TeacherDashboardResponse();
            response.setCourseCount(course.size());
            response.setEnrolledStudentCount(enrollmentList.size());
            response.setFullName(teacher.getUser().getFullName());
            return GenericResponse.createSuccessResponse("Teacher Dashboard Data Fetch Successfully!", response, HttpStatus.OK.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("teacherDashboard() -> Exception occurred.");
            throw new BusinessException("teacherDashboard() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse studentDashboard(long studentId) {
        try {
            logger.info("studentDashboard() -> request received.");
            // Fetch student details
            Student student = studentRepo.getReferenceById(studentId);

            // Fetch active course enrollments
            List<Enrollment> enrollments = enrollmentRepo.findByStudentIdAndFlag(studentId, Flag.ACTIVE);
            List<Long> courseIds = enrollments.stream()
                    .map(enrollment -> enrollment.getCourse().getId())
                    .toList();

            // Fetch all assignments for the student's courses
            List<Assignment> allAssignments = assignmentRepo.findByCourseIdInAndFlag(courseIds, Flag.ACTIVE);

            // Fetch all assignment uploads for the student
            List<AssignmentUpload> assignmentUploads = assignmentUploadRepo.findByStudentIdAndFlagAndStatusAndCourseIds(
                    studentId, Flag.ACTIVE, List.of(AssignmentStatus.SUBMITTED, AssignmentStatus.GRADED), courseIds);

            // Calculate assignment count, increment for missing records
            int assignmentCount = allAssignments.size() - assignmentUploads.size();

            // Fetch all quizzes for the student's courses
            List<Quiz> allQuizzes = quizRepo.findByCourseIdInAndFlag(courseIds, Flag.ACTIVE);

            // Fetch all quiz submissions for the student
            List<QuizSubmission> quizSubmissions = quizSubmissionRepo.findByStudentIdAndFlagAndStatus(
                    studentId, Flag.ACTIVE, QuizStatus.SUBMITTED);

            // Calculate quiz count, increment for missing records
            int quizCount = allQuizzes.size() - quizSubmissions.size();

            // Prepare the response
            StudentDashboardResponse response = new StudentDashboardResponse();
            response.setFullName(student.getUser().getFullName());
            response.setAssignmentCount(assignmentCount);
            response.setQuizCount(quizCount);

            logger.info("studentDashboard() -> Data fetched successfully.");
            return GenericResponse.createSuccessResponse(
                    "Student Dashboard Data Fetch Successfully!",
                    response,
                    HttpStatus.OK.value()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("studentDashboard() -> Exception occurred.");
            throw new BusinessException("studentDashboard() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}

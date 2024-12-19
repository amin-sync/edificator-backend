package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.enrollment.CreateEnrollmentRequest;
import com.hub.edificators.dtos.responses.enrollment.EnrolledCoursesResponse;
import com.hub.edificators.entities.Course;
import com.hub.edificators.entities.Enrollment;
import com.hub.edificators.entities.Student;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.repos.EnrollmentRepo;
import com.hub.edificators.repos.StudentRepo;
import com.hub.edificators.services.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Value("${profile.pic.host}")
    private String profilePicHost;

    @Override
    public GenericResponse createEnrollment(CreateEnrollmentRequest request) {
        try {
            logger.info("createEnrollment() -> request received.");
            Enrollment enrollment = new Enrollment();
            Course course = courseRepo.getReferenceById(request.getCourseId());
            enrollment.setCourse(course);
            Student student = studentRepo.getReferenceById(request.getStudentId());
            enrollment.setStudent(student);
            enrollment.setPayment(request.getPayment());
            enrollmentRepo.save(enrollment);
            logger.info("createEnrollment() -> request completed.");
            return GenericResponse.createSuccessResponse("Enrollment Created successfully", null, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createEnrollment() -> Exception occurred.");
            throw new BusinessException("createEnrollment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listEnrolledCourses(Long studentId) {
        try {
            logger.info("listEnrolledCourses() -> request received.");

            List<Enrollment> enrollmentList = enrollmentRepo.findByStudentIdAndFlag(studentId,Flag.ACTIVE);
            List<EnrolledCoursesResponse> responseList = new ArrayList<>();
            for (Enrollment i : enrollmentList) {
                Course course = i.getCourse();
                EnrolledCoursesResponse response = new EnrolledCoursesResponse();
                response.setCourseId(course.getId());
                response.setSubject(course.getSubject());
                response.setName(course.getTeacher().getUser().getFullName());
                if (course.getTeacher().getUser().getProfilePicFileName() != null) {
                    response.setProfileUrl(course.getTeacher().getUser().getProfilePicURl(profilePicHost));
                }
                responseList.add(response);

            }
            logger.info("listEnrolledCourses() -> request completed.");
            return GenericResponse.createSuccessResponse("listEnrolledCourses Fetched successfully", responseList, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listEnrolledCourses() -> Exception occurred.");
            throw new BusinessException("listEnrolledCourses() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}

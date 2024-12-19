package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericModelMapper;
import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.course.CreateCourseRequest;
import com.hub.edificators.dtos.requests.course.UpdateCourseRequest;
import com.hub.edificators.dtos.responses.course.CourseDetailsResponse;
import com.hub.edificators.dtos.responses.course.ListGradeByStudentIdResponse;
import com.hub.edificators.dtos.responses.course.TeacherCourseListResponse;
import com.hub.edificators.entities.Course;
import com.hub.edificators.entities.Enrollment;
import com.hub.edificators.entities.Student;
import com.hub.edificators.entities.Teacher;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.repos.EnrollmentRepo;
import com.hub.edificators.repos.StudentRepo;
import com.hub.edificators.repos.TeacherRepo;
import com.hub.edificators.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Value("${profile.pic.host}")
    private String profilePicHost;

    @Override
    public GenericResponse createCourse(CreateCourseRequest request) {
        try {
            logger.info("createCourse() -> request received.");
            Teacher teacher = teacherRepo.getReferenceById(request.getTeacherId());
            Course course = Course.builder().days(request.getDays()).fee(request.getFee()).fromTime(request.getFromTime()).toTime(request.getToTime()).grade(request.getGrade()).subject(request.getSubject()).teacher(teacher).build();
            courseRepo.save(course);
            logger.info("createCourse() -> request completed.");
            return GenericResponse.createSuccessResponse("Course Created successfully", null, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createCourse() -> Exception occurred.");
            throw new BusinessException("createCourse() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse updateCourse(UpdateCourseRequest request) {
        try {
            logger.info("updateCourse() -> request received.");
            Course course = courseRepo.getReferenceById(request.getCourseId());
            course.setGrade(request.getGrade() == null ? course.getGrade() : request.getGrade());
            course.setSubject(request.getSubject() == null ? course.getSubject() : request.getSubject());
            course.setDays(request.getDays() == null ? course.getDays() : request.getDays());
            course.setFromTime(request.getFromTime() == null ? course.getToTime() : request.getFromTime());
            course.setToTime(request.getToTime() == null ? course.getToTime() : request.getToTime());
            course.setFee(request.getFee() == null ? course.getFee() : request.getFee());
            courseRepo.save(course);
            logger.info("updateCourse() -> request completed.");
            return GenericResponse.createSuccessResponse("Course Update successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("updateCourse() -> Exception occurred.");
            throw new BusinessException("updateCourse() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listTeacherCourse(Long teacherId) {
        try {
            logger.info("listTeacherCourse() -> request received.");
            List<Course> courseList = courseRepo.findByTeacherId(teacherId);
            List<TeacherCourseListResponse> responseList = new ArrayList<>();
            for (Course course : courseList) {
                TeacherCourseListResponse response = GenericModelMapper.mapObject(course, TeacherCourseListResponse.class);
                responseList.add(response);
            }
            logger.info("listTeacherCourse() -> request completed.");
            return GenericResponse.createSuccessResponse("Teacher Courses fetched successfully", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listTeacherCourse() -> Exception occurred.");
            throw new BusinessException("listTeacherCourse() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse courseDetails(Long courseId) {
        try {
            logger.info("courseDetails() -> request received.");
            Course course = courseRepo.getReferenceById(courseId);
            CourseDetailsResponse response = new CourseDetailsResponse();
            response.setFullName(course.getTeacher().getUser().getFullName());
            if (course.getTeacher().getUser().getProfilePicFileName() != null) {
                response.setProfileUrl(course.getTeacher().getUser().getProfilePicURl(profilePicHost));
            }
            response.setExpertise(course.getTeacher().getExpertise());
            response.setSubject(course.getSubject());
            response.setGrade(course.getGrade());
            response.setDays(course.getDays());
            response.setFromTime(course.getFromTime());
            response.setToTime(course.getToTime());
            response.setFee(course.getFee());
            logger.info("courseDetails() -> request completed.");
            return GenericResponse.createSuccessResponse("Course Details fetched successfully", response, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("courseDetails() -> Exception occurred.");
            throw new BusinessException("courseDetails() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listGradeCourse(Long studentId) {
        try {
            logger.info("listGradeCourse() -> request received.");
            Student student = studentRepo.getReferenceById(studentId);
            String grade = student.getGrade();
            List<Course> courseList = courseRepo.findByGrade(grade);
            List<ListGradeByStudentIdResponse> responseList = new ArrayList<>();
            for (Course i : courseList) {
                Enrollment enrollment = enrollmentRepo.findByCourseIdAndStudentId(i.getId(), student.getId());
                if(enrollment ==null){
                    ListGradeByStudentIdResponse response = new ListGradeByStudentIdResponse();
                    if (i.getTeacher().getUser().getProfilePicFileName() != null) {
                        response.setProfileUrl(i.getTeacher().getUser().getProfilePicURl(profilePicHost));
                    }
                    response.setTeacherName(i.getTeacher().getUser().getFullName());
                    response.setSubject(i.getSubject());
                    response.setId(i.getId());
                    responseList.add(response);
                }
            }
            logger.info("listGradeCourse() -> request completed.");
            return GenericResponse.createSuccessResponse("Course List fetched successfully", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listGradeCourse() -> Exception occurred.");
            throw new BusinessException("listGradeCourse() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}

package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.BaseEntity;
import com.hub.edificators.commons.GenericModelMapper;
import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.constants.AppConstants;
import com.hub.edificators.dtos.requests.user.LoginRequest;
import com.hub.edificators.dtos.requests.user.RegisterStudentRequest;
import com.hub.edificators.dtos.requests.user.RegisterTeacherRequest;
import com.hub.edificators.dtos.requests.user.UpdatePasswordRequest;
import com.hub.edificators.dtos.responses.course.TeacherCourseListResponse;
import com.hub.edificators.dtos.responses.user.ListStudentsResponse;
import com.hub.edificators.dtos.responses.user.ListTeacherResponse;
import com.hub.edificators.dtos.responses.user.LoginResponse;
import com.hub.edificators.dtos.responses.user.UserProfileResponse;
import com.hub.edificators.entities.*;
import com.hub.edificators.enums.AccessLevel;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.repos.*;
import com.hub.edificators.services.UserService;
import com.hub.edificators.utils.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Value("${profile.pic.host}")
    private String profilePicHost;

    @Override
    public GenericResponse getProfile(Long userId) {
        try {
            logger.info("getProfile() -> request received.");
            User user = userRepo.getReferenceById(userId);
            UserProfileResponse userResponse = GenericModelMapper.mapObject(user, UserProfileResponse.class);
            if (user.getProfilePicFileName() != null) {
                userResponse.setProfileURL(user.getProfilePicURl(profilePicHost));
            }
            logger.info("getProfile() -> request completed.");
            return GenericResponse.createSuccessResponse("User Profile Fetch Successfully! ", userResponse, HttpStatus.OK.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("getProfile() -> Exception occurred.");
            throw new BusinessException("getProfile() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse updatePassword(UpdatePasswordRequest request) {
        try {
            logger.info("updatePassword() -> request received.");
            User user = userRepo.getReferenceById(request.getUserId());
            if(user.getPassword().equals(request.getOldPassword()) ){
                user.setPassword(request.getNewPassword());
                userRepo.save(user);
                logger.info("updatePassword() -> request completed.");
                return GenericResponse.createSuccessResponse("Password Updated!", null, HttpStatus.OK.value());
            } else{
                logger.info("updatePassword() -> Invalid Current password.");
                return GenericResponse.createErrorResponse("Current password doesn't match with old password!", null, HttpStatus.UNAUTHORIZED.value());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("updatePassword() -> Exception occurred.");
            throw new BusinessException("updatePassword() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse registerTeacher(RegisterTeacherRequest request) {
        try {
            logger.info("registerTeacher() -> request received.");
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFullName(request.getFullName());
            user.setNic(request.getNic());
            Role role = roleRepo.getReferenceById(request.getRoleId());
            user.setRole(role);
            user = userRepo.save(user);

            Teacher teacher = new Teacher();
            teacher.setExpertise(request.getExpertise());
            teacher.setUser(user);
            teacherRepo.save(teacher);

            logger.info("registerTeacher() -> request completed.");
            return GenericResponse.createSuccessResponse("Teacher Created!", user.getId(), HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("registerTeacher() -> Exception occurred.");
            throw new BusinessException("registerTeacher() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse registerStudent(RegisterStudentRequest request) {
        try {
            logger.info("registerStudent() -> request received.");
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFullName(request.getFullName());
            user.setNic(request.getNic());
            Role role = roleRepo.getReferenceById(request.getRoleId());
            user.setRole(role);
            user = userRepo.save(user);

            Student student = new Student();
            student.setGrade(request.getGrade());
            student.setUser(user);
            studentRepo.save(student);

            logger.info("registerStudent() -> request completed.");
            return GenericResponse.createSuccessResponse("Student Created!", user.getId(), HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("registerStudent() -> Exception occurred.");
            throw new BusinessException("registerStudent() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse userLogin(LoginRequest request) {
        try {
            logger.info("userLogin() -> request received.");
            User user = userRepo.findByEmail(request.getEmail());
            if (user.getEmail().equals(request.getEmail()) && user.getPassword().equals(request.getPassword())) {
                long associateId;
                if (user.getRole().getName().equals(AccessLevel.TEACHER)) {
                    Teacher teacher = teacherRepo.findByUserId(user.getId());
                    associateId = teacher.getId();
                } else {
                    Student student = studentRepo.findByUserId(user.getId());
                    associateId = student.getId();
                }
                LoginResponse response = LoginResponse.builder().userId(user.getId()).associateId(associateId).roleId(user.getRole().getId()).isLogin(true).build();
                logger.info("userLogin() -> request completed.");
                return GenericResponse.createSuccessResponse("User Login Successfully!", response, HttpStatus.OK.value());
            } else {
                logger.info("userLogin() -> Invalid email or password.");
                return GenericResponse.createErrorResponse("Invalid email or password.", null, HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("userLogin() -> Exception occurred.");
            throw new BusinessException("userLogin() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse uploadProfilePic(Long userId, MultipartFile file) {
        try {
            logger.info("uploadProfilePic() -> request received.");

            String fileName = FileUtility.saveFile(userId, AppConstants.PROFILE_PIC_DIRECTORY, file);
            User user = userRepo.getReferenceById(userId);
            user.setProfilePicFileName(fileName);
            userRepo.save(user);

            logger.info("uploadProfilePic() -> request completed.");
            return GenericResponse.createSuccessResponse("Profile picture uploaded Successfully!", true, HttpStatus.OK.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("uploadProfilePic() -> Exception occurred.");
            throw new BusinessException("uploadProfilePic() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listStudentsByTeacherId(Long teacherId) {
        try {
            logger.info("listStudentsByTeacherId() -> request received.");
            List<Course> courseList = courseRepo.findByTeacherId(teacherId);
            List<Long> courseIds = courseList.stream().map(BaseEntity::getId).toList();
            List<Enrollment> enrollments = enrollmentRepo.findByCourseIdsAndFlag(courseIds, Flag.ACTIVE);
            List<ListStudentsResponse> responses = new ArrayList<>();
            for(Enrollment enrollment : enrollments){
                User user = enrollment.getStudent().getUser();
                responses.add(ListStudentsResponse.builder().fullName(user.getFullName()).id(user.getId()).profileURL(user.getProfilePicFileName() != null ? user.getProfilePicURl(profilePicHost) : null).build());
            }
            logger.info("listStudentsByTeacherId() -> request completed.");
            return GenericResponse.createSuccessResponse("Students List fetched successfully", responses, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listStudentsByTeacherId() -> Exception occurred.");
            throw new BusinessException("listStudentsByTeacherId() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listTeachersByStudentId(Long studentId) {
        try {
            logger.info("listTeachersByStudentId() -> request received.");
            List<Enrollment> enrollments = enrollmentRepo.findByStudentIdAndFlag(studentId, Flag.ACTIVE);
            List<Teacher> teachers = enrollments.stream()
                    .map(enrollment -> enrollment.getCourse().getTeacher())
                    .toList();
            List<ListTeacherResponse> responses = new ArrayList<>();
            for(Teacher teacher : teachers){
                User user = teacher.getUser();
                responses.add(ListTeacherResponse.builder().fullName(user.getFullName()).id(user.getId()).profileURL(user.getProfilePicFileName() != null ? user.getProfilePicURl(profilePicHost) : null).build());
            }
            logger.info("listTeachersByStudentId() -> request completed.");
            return GenericResponse.createSuccessResponse("Students List fetched successfully", responses, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listTeachersByStudentId() -> Exception occurred.");
            throw new BusinessException("listTeachersByStudentId() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}


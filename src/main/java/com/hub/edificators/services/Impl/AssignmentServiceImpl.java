package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericModelMapper;
import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.assignment.GradeAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.CreateAssignmentRequest;
import com.hub.edificators.dtos.requests.assignment.ListStudentAssignmentRequest;
import com.hub.edificators.dtos.responses.assignment.AssignmentStatusListResponse;
import com.hub.edificators.constants.AppConstants;
import com.hub.edificators.dtos.requests.assignment.SubmitAssignmentRequest;
import com.hub.edificators.dtos.responses.assignment.CourseAssignmentListResponse;
import com.hub.edificators.dtos.responses.assignment.StudentAssignmentListResponse;
import com.hub.edificators.entities.Assignment;
import com.hub.edificators.entities.AssignmentUpload;
import com.hub.edificators.entities.Course;
import com.hub.edificators.entities.Enrollment;
import com.hub.edificators.enums.AssignmentStatus;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.repos.AssignmentRepo;
import com.hub.edificators.repos.AssignmentUploadRepo;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.repos.EnrollmentRepo;
import com.hub.edificators.repos.StudentRepo;
import com.hub.edificators.services.AssignmentService;
import com.hub.edificators.utils.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AssignmentUploadRepo assignmentUploadRepo;

    @Override
    public GenericResponse createAssignment(CreateAssignmentRequest request, MultipartFile file) {
        try {
            logger.info("createAssignment() -> request received.");

            Course course = courseRepo.getReferenceById(Long.valueOf(request.getCourseId()));
            Assignment assignment = new Assignment();
            assignment.setTitle(request.getTitle());
            assignment.setDueDate(request.getDueDate());
            assignment.setCourse(course);
            assignment = assignmentRepo.save(assignment);

            String fileName = FileUtility.saveFile(assignment.getId(), AppConstants.ASSIGNMENT_FILE_DIRECTORY, file);
            assignment.setFileName(fileName);
            assignmentRepo.save(assignment);

            logger.info("createAssignment() -> request completed.");
            return GenericResponse.createSuccessResponse("Assignment Created successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createAssignment() -> Exception occurred.");
            throw new BusinessException("createAssignment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse deleteAssignment(Long assignmentId) {
        try {
            logger.info("deleteAssignment() -> request received.");
            Assignment assignment = assignmentRepo.getReferenceById(assignmentId);
            assignment.setFlag(Flag.INACTIVE);
            assignmentRepo.save(assignment);
            logger.info("deleteAssignment() -> request completed.");
            return GenericResponse.createSuccessResponse("Assignment Deleted successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("deleteAssignment() -> Exception occurred.");
            throw new BusinessException("deleteAssignment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listCourseAssignment(long courseId) {
        try {
            logger.info("listCourseAssignment() -> request received.");
            List<Assignment> assignmentList = assignmentRepo.findByCourseIdAndFlag(courseId, Flag.ACTIVE);
            List<CourseAssignmentListResponse> responseList = new ArrayList<>();
            for (Assignment i : assignmentList) {
                CourseAssignmentListResponse response = GenericModelMapper.mapObject(i, CourseAssignmentListResponse.class);
                responseList.add(response);
            }
            logger.info("listCourseAssignment() -> request completed.");
            return GenericResponse.createSuccessResponse("Courses Assignment fetched successfully", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listCourseAssignment() -> Exception occurred.");
            throw new BusinessException("listCourseAssignment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse assignmentStatusList(long assignmentId) {
        try {
            logger.info("assignmentStatusList() -> request received.");
            Assignment assignment = assignmentRepo.getReferenceById(assignmentId);
            List<AssignmentStatusListResponse> responseList = new ArrayList<>();
            List<Enrollment> enrollmentList = enrollmentRepo.findByCourseIdAndFlag(assignment.getCourse().getId(), Flag.ACTIVE);
            for (Enrollment enrollment : enrollmentList) {
                AssignmentStatusListResponse response = new AssignmentStatusListResponse();
                response.setFullName(enrollment.getStudent().getUser().getFullName());

                AssignmentUpload upload = assignmentUploadRepo.
                        findByStudentIdAndAssignmentId(enrollment.getStudent().getId(), assignmentId);
                if (upload != null) {
                    response.setStatus(upload.getStatus().toString());
                    response.setFileName(upload.getFileName());
                } else {
                    response.setStatus(String.valueOf(AssignmentStatus.PENDING));
                }
                responseList.add(response);
            }
            logger.info("assignmentStatusList() -> request completed.");
            return GenericResponse.createSuccessResponse("Assignment Status List fetched successfully", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("assignmentStatusList() -> Exception occurred.");
            throw new BusinessException("assignmentStatusList() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse assignmentGrade(GradeAssignmentRequest request) {
        try {
            logger.info("assignmentGrade() -> request received.");
            AssignmentUpload assignmentUpload = assignmentUploadRepo.findByStudentIdAndAssignmentId(request.getStudentId(), request.getAssignmentId());
            assignmentUpload.setMarks(request.getMarks());
            assignmentUpload.setStatus(AssignmentStatus.GRADED);
            assignmentUploadRepo.save(assignmentUpload);
            logger.info("assignmentGrade() -> request completed.");
            return GenericResponse.createSuccessResponse("Assignment Graded successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("assignmentGrade() -> Exception occurred.");
            throw new BusinessException("assignmentGrade() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    @Override
    public GenericResponse listStudentAssignment(ListStudentAssignmentRequest request) {
        try {
            logger.info("listStudentAssignment() -> request received.");
            List<Assignment> assignmentList=assignmentRepo.findByCourseIdAndFlag(request.getCourseId(),Flag.ACTIVE);
            List<StudentAssignmentListResponse> responseList = new ArrayList<>();
            for(Assignment assignment:assignmentList){
                StudentAssignmentListResponse response=new StudentAssignmentListResponse();
                response.setTitle(assignment.getTitle());
                response.setDueDate(assignment.getDueDate());
                response.setFileName(assignment.getFileName());
                response.setId(assignment.getId());
                AssignmentUpload assignmentUpload=assignmentUploadRepo.findByStudentIdAndAssignmentId(request.getStudentId(), assignment.getId());
                if (assignmentUpload != null && assignmentUpload.getStatus() != null) {
                    response.setStatus(String.valueOf(assignmentUpload.getStatus()));
                    response.setMarks(assignmentUpload.getMarks() == null ? "0" : assignmentUpload.getMarks());
                } else {
                    response.setStatus(AssignmentStatus.PENDING.name());
                    response.setMarks("0");
                }
                responseList.add(response);
            }
            logger.info("listStudentAssignment() -> request completed.");
            return GenericResponse.createSuccessResponse("Student Assignment list Fetched successfully!", responseList, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listStudentAssignment() -> Exception occurred.");
            throw new BusinessException("listStudentAssignment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public GenericResponse submitAssignment(SubmitAssignmentRequest request, MultipartFile file) {
        try {
            logger.info("submitAssignment() -> request received.");

            AssignmentUpload assignmentUpload = new AssignmentUpload();
            assignmentUpload.setStudent(studentRepo.getReferenceById(request.getStudentId()));
            assignmentUpload.setAssignment(assignmentRepo.getReferenceById(request.getAssignmentId()));
            assignmentUpload.setStatus(AssignmentStatus.SUBMITTED);
            assignmentUploadRepo.save(assignmentUpload);

            String fileName = FileUtility.saveFile(assignmentUpload.getId(), AppConstants.UPLOAD_FILE_DIRECTORY, file);
            assignmentUpload.setFileName(fileName);
            assignmentUploadRepo.save(assignmentUpload);

            logger.info("submitAssignment() -> request completed.");
            return GenericResponse.createSuccessResponse("Assignment Submitted successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("submitAssignment() -> Exception occurred.");
            throw new BusinessException("deleteAssignment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());

        }
    }
}



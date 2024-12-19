package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.announcement.CreateAnnouncementRequest;
import com.hub.edificators.dtos.responses.announcement.TeacherAnnouncementResponse;
import com.hub.edificators.entities.Announcement;
import com.hub.edificators.entities.Course;
import com.hub.edificators.repos.AnnouncementRepo;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public GenericResponse createAnnouncement(CreateAnnouncementRequest request) {
        try {
            logger.info("createAnnouncement() -> request received.");
            Announcement announcement = new Announcement();
            announcement.setMessage(request.getMessage());
            Course course = courseRepo.getReferenceById(request.getCourseId());
            announcement.setCourse(course);
            announcementRepo.save(announcement);
            logger.info("createAnnouncement() -> request completed.");
            return GenericResponse.createSuccessResponse("Announcement Created successfully", null, HttpStatus.CREATED.value());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createAnnouncement() -> Exception occurred.");
            throw new BusinessException("createAnnouncement() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse listTeacherAnnouncement(Long courseId) {
        try{
            logger.info("listAnnouncementRequest() -> request received.");
            List<Announcement> announcementList=announcementRepo.findByCourseId(courseId);
            List<TeacherAnnouncementResponse> responseList=new ArrayList<>();
            for(Announcement i: announcementList){
                TeacherAnnouncementResponse response=new TeacherAnnouncementResponse();
                response.setMessage(i.getMessage());
                response.setCreatedDate(i.getCreatedOn());
                responseList.add(response);
            } logger.info("listAnnouncementRequest() -> request completed.");
            return GenericResponse.createSuccessResponse("Teacher's Announcments fetched successfully", responseList, HttpStatus.CREATED.value());
        }catch (Exception ex) {
            ex.printStackTrace();
            logger.error("listAnnouncementRequest() -> Exception occurred.");
            throw new BusinessException("listAnnouncementRequest() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}

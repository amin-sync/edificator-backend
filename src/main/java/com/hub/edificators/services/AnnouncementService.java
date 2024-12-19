package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.announcement.CreateAnnouncementRequest;
import org.springframework.stereotype.Service;

@Service
public interface
AnnouncementService {
    GenericResponse createAnnouncement(CreateAnnouncementRequest request);
    GenericResponse listTeacherAnnouncement(Long courseId);
}

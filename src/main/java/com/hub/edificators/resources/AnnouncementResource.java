package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.announcement.CreateAnnouncementRequest;
import com.hub.edificators.services.AnnouncementService;
import com.hub.edificators.validators.AnnouncementResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcement")
public class AnnouncementResource {

    @Autowired
    private AnnouncementResourceValidator announcementResourceValidator;

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/create")
    public ResponseEntity createAnnouncement(@RequestBody CreateAnnouncementRequest request){
        announcementResourceValidator.validate(request);
        return ResponseEntity.ok(announcementService.createAnnouncement(request));
       }

    @GetMapping("/list-by-teacher")
    public ResponseEntity ListTeacherAnnouncement(@RequestParam Long courseId){
        announcementResourceValidator.validate("Teacher ID",courseId.toString());
        return ResponseEntity.ok(announcementService.listTeacherAnnouncement(courseId));
    }
}

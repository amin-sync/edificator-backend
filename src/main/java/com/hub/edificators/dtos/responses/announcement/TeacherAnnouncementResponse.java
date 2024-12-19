package com.hub.edificators.dtos.responses.announcement;

import lombok.Data;

import java.util.Date;
@Data
public class TeacherAnnouncementResponse {
    private String message;
    private Date createdDate;
}

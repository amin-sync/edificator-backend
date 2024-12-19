package com.hub.edificators.dtos.requests.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAnnouncementRequest {

    private String message;
    private Long courseId;
}

package com.hub.edificators.dtos.responses.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrolledCoursesResponse {
    private long courseId;
    private String subject;
    private String profileUrl;
    private String name;
}

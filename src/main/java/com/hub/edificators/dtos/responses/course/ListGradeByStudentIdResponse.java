package com.hub.edificators.dtos.responses.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListGradeByStudentIdResponse {
    private long id;
    private String profileUrl;
    private String subject;
    private String teacherName;
}

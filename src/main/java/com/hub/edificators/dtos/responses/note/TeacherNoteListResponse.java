package com.hub.edificators.dtos.responses.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherNoteListResponse {
    private long id;
    private String title;
    private Date createdOn;
    private String fileName;
}

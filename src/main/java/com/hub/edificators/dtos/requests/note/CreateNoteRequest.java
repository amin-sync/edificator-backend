package com.hub.edificators.dtos.requests.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateNoteRequest {

    private String title;
    private String courseId;

}

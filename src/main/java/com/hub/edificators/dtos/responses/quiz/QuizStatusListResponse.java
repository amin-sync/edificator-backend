package com.hub.edificators.dtos.responses.quiz;

import com.hub.edificators.enums.QuizStatus;
import lombok.Data;

@Data
public class QuizStatusListResponse {
    private String fullName;
    private String marks;
    private String outOf;
    private String status;
}

package com.hub.edificators.dtos.requests.enrollment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnenrollRequest {

    private long studentId;
    private long courseId;
}

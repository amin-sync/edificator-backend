package com.hub.edificators.dtos.requests.enrollment;

import com.hub.edificators.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateEnrollmentRequest {

    private Payment payment;
    private long studentId;
    private long courseId;
}

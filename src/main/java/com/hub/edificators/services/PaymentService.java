package com.hub.edificators.services;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.dtos.requests.enrollment.UnenrollRequest;
import com.hub.edificators.dtos.requests.payment.CreatePaymentRequest;

public interface PaymentService {
    GenericResponse createPayment(CreatePaymentRequest request);
    GenericResponse unenrollment(UnenrollRequest request);
}

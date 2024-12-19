package com.hub.edificators.services.Impl;

import com.hub.edificators.commons.GenericResponse;
import com.hub.edificators.commons.exceptions.BusinessException;
import com.hub.edificators.dtos.requests.enrollment.CreateEnrollmentRequest;
import com.hub.edificators.dtos.requests.enrollment.UnenrollRequest;
import com.hub.edificators.dtos.requests.payment.CreatePaymentRequest;
import com.hub.edificators.entities.Enrollment;
import com.hub.edificators.entities.Payment;
import com.hub.edificators.enums.Flag;
import com.hub.edificators.enums.PaymentStatus;
import com.hub.edificators.repos.CourseRepo;
import com.hub.edificators.repos.EnrollmentRepo;
import com.hub.edificators.repos.PaymentRepo;
import com.hub.edificators.repos.StudentRepo;
import com.hub.edificators.services.EnrollmentService;
import com.hub.edificators.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public GenericResponse createPayment(CreatePaymentRequest request) {
        try {
            logger.info("createPayment()-> Request Received.!");
            Payment payment = new Payment();
            payment.setNameOnCard(request.getNameOnCard());
            payment.setCardNumber(request.getCardNumber());
            payment.setCvc(request.getCvc());
            payment.setExpiryMonth(request.getExpiryMonth());
            payment.setExpiryYear(request.getExpiryYear());
            payment.setPayment(request.getPayment());
            payment.setStatus(PaymentStatus.PAID);
            payment = paymentRepo.save(payment);

            CreateEnrollmentRequest enrollmentRequest = new CreateEnrollmentRequest();
            enrollmentRequest.setPayment(payment);
            enrollmentRequest.setStudentId(request.getStudentId());
            enrollmentRequest.setCourseId(request.getCourseId());
            enrollmentService.createEnrollment(enrollmentRequest);

            logger.info("createPayment() -> request completed.");
            return GenericResponse.createSuccessResponse("Transaction Completed successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("createPayment() -> Exception occurred.");
            throw new BusinessException("createPayment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public GenericResponse unenrollment(UnenrollRequest request) {
        try {
            logger.error("unenrollment()-> Request Received.!");
            Enrollment enrollment = enrollmentRepo.findByCourseIdAndStudentId(request.getCourseId(), request.getStudentId());
            enrollmentRepo.delete(enrollment);
            logger.info("unenrollment() -> request completed.");
            return GenericResponse.createSuccessResponse("Unenrollment Completed successfully", null, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("unenrollment() -> Exception occurred.");
            throw new BusinessException("unenrollment() -> Exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}

package com.hub.edificators.resources;

import com.hub.edificators.dtos.requests.enrollment.UnenrollRequest;
import com.hub.edificators.dtos.requests.payment.CreatePaymentRequest;
import com.hub.edificators.services.PaymentService;
import com.hub.edificators.validators.PaymentResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentResource {

    @Autowired
    private PaymentResourceValidator paymentResourceValidator;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity createPayment(@RequestBody CreatePaymentRequest request){
        paymentResourceValidator.validate(request);
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PostMapping("/cancel-enrollment")
    public ResponseEntity unenrollment(@RequestBody UnenrollRequest request){
        paymentResourceValidator.validate(request);
        return ResponseEntity.ok(paymentService.unenrollment(request));
    }
}

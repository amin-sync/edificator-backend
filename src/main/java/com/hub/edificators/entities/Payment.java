package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import com.hub.edificators.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "payments")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Column(name = "payment_status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cvc")
    private String cvc;

    @Column(name = "expiry_month")
    private String expiryMonth;

    @Column(name = "expiry_year")
    private String expiryYear;

    @Column(name = "payment")
    private String payment;

}

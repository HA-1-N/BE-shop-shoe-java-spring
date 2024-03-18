package com.example.shopshoejavaspring.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "user_payment")
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id")
    private PaymentType paymentType;

    @Column(name = "provider")
    private String provider;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "expiry")
    private int expiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="payment_modthod_id", referencedColumnName = "id")
    private Order order;

}

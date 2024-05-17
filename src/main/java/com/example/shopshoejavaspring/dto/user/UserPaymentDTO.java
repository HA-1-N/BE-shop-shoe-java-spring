package com.example.shopshoejavaspring.dto.user;

import com.example.shopshoejavaspring.dto.paymentType.PaymentTypeDTO;
import com.example.shopshoejavaspring.entity.PaymentType;
import lombok.Data;

@Data
public class UserPaymentDTO {

    private Long id;

    private PaymentTypeDTO paymentType;

    private String provider;

    private String accountNumber;

    private int expiry;

    private Long userId;

}

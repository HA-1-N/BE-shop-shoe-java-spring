package com.example.shopshoejavaspring.dto.paymentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentTypeDTO {

    private Long id;
    private String type;
}

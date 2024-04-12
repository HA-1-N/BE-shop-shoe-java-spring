package com.example.shopshoejavaspring.dto.user;

import lombok.Data;

@Data
public class UserPaymentDTO {

    private Long id;

    private String type;

    private String provider;

    private String accountNumber;

    private int expiry;

    private Long userId;

}

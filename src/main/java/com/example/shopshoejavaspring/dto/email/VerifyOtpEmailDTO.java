package com.example.shopshoejavaspring.dto.email;

import lombok.Data;

@Data
public class VerifyOtpEmailDTO {

    private Long otp;
    private String email;

}

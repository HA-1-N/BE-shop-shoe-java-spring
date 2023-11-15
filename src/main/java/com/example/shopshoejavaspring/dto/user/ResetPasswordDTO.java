package com.example.shopshoejavaspring.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetPasswordDTO {
    private String email;
    private String password;
}

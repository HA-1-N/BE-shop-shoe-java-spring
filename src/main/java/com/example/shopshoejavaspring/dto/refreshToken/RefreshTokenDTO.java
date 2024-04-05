package com.example.shopshoejavaspring.dto.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RefreshTokenDTO {
    private Long id;
    private String token;
    private String expiredDate;
}

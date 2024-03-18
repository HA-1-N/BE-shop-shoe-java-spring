package com.example.shopshoejavaspring.dto.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RequestRefreshTokenDTO {

    private String refreshToken;

}

package com.example.shopshoejavaspring.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;

    @NotNull(message = "Mã vai trò không được để trống")
    private String code;

    @NotNull(message = "Tên vai trò không được để trống")
    private String text;

}

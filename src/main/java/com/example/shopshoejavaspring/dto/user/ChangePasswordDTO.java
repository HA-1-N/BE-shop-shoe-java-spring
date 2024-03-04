package com.example.shopshoejavaspring.dto.user;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private Long id;

    private String oldPassword;

    private String newPassword;

}

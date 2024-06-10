package com.example.shopshoejavaspring.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRoleDTO {

    private List<Long> roleIds;

}

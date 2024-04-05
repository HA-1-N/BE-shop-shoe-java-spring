package com.example.shopshoejavaspring.dto.user;

import com.example.shopshoejavaspring.dto.role.RoleDTO;
import com.example.shopshoejavaspring.enumerations.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ListUserDTO {

    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Integer age;

    private String prefix;

    private String phone;

    private Date dateOfBirth;

    private String image;

    private List<RoleDTO> roles = new ArrayList<>();

}

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

@Data // generate getter, setter, toString, equals, hashcode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Email không được để trống")
    private String email;

    @NotNull(message = "Mật khẩu không được để trống")
    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Integer age;

    private String prefix;

    private String phone;

    private Date dateOfBirth;

    private String image;

    private String token;

    private String refreshToken;

    private List<Long> roleIds = new ArrayList<>();

    private List<RoleDTO> roles = new ArrayList<>();

}

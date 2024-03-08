package com.example.shopshoejavaspring.dto.user;

import com.example.shopshoejavaspring.enumerations.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailUpdateDTO {

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

}

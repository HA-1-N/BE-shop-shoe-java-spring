package com.example.shopshoejavaspring.dto.user;

import com.example.shopshoejavaspring.enumerations.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
public class UserMobileRegisterDTO {

    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String prefix;
    private String phone;
    private Integer age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private List<Long> roleIds;

//    private String image;

}

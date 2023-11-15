package com.example.shopshoejavaspring.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Date;

@Data
public class FilterUserDTO {

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private String gender;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

}

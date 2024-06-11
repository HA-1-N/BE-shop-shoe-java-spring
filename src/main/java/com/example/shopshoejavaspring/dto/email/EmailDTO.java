package com.example.shopshoejavaspring.dto.email;

import lombok.Data;

@Data
public class EmailDTO {

    private String to;
    private String subject;
    private String body;

}

package com.example.shopshoejavaspring.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // generate getter, setter, toString, equals, hashcode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLoginDTO {

        private String email;

        private String password;
}

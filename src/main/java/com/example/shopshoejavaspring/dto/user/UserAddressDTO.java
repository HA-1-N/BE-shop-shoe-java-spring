package com.example.shopshoejavaspring.dto.user;

import lombok.Data;

@Data
public class UserAddressDTO {

        private Long id;

        private String name;

        private String address;

        private String city;

        private String country;

        private String phone;

        private String prefix;

        private String note;

        private Long userId;
}

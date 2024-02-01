package com.example.shopshoejavaspring.dto.banner;

import lombok.Data;

@Data
public class CreateBannerDTO {

    private Long id;

    private String title;

    private String image;

    private String content;

    private String link;

    private Long status;

    private String position;

    private String created_at;

    private String updated_at;

}

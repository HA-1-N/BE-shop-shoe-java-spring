package com.example.shopshoejavaspring.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CreateProductDTO {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private Long status;

    private Long brandId;

    private Long categoryId;

}

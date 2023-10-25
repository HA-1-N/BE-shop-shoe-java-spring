package com.example.shopshoejavaspring.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FilterProductDTO {

    private String name;

    private Long status;

    private Long categoryId;

    private Long brandId;

    private List<Long> sizeId;

    private List<Long> colorId;

    private Double minPrice;

    private Double maxPrice;

}

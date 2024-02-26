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
public class FilterProductWebsiteDTO {

    private String name;

    private Long status;

    private List<Long> categoryId;

    private List<Long> brandId;

    private List<Long> sizeId;

    private List<Long> colorId;

    private Double minPrice;

    private Double maxPrice;
}

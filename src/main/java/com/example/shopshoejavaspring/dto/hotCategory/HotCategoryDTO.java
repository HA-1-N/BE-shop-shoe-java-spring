package com.example.shopshoejavaspring.dto.hotCategory;

import com.example.shopshoejavaspring.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HotCategoryDTO {
    private Long id;

    private String name;
}

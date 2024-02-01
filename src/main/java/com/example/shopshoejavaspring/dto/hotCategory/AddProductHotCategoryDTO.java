package com.example.shopshoejavaspring.dto.hotCategory;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AddProductHotCategoryDTO {
    private List<Long> productIds;
}

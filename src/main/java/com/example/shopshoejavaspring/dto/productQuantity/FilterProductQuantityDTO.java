package com.example.shopshoejavaspring.dto.productQuantity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FilterProductQuantityDTO {
    private List<Long> productId;
    private List<Long> colorId;
    private List<Long> sizeId;
    private Long status;
}

package com.example.shopshoejavaspring.dto.productQuantity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateProductQuantityDTO {
    private Long id;

    private Long quantity;

    private Long status;

    private Long sizeId;

    private Long colorId;

    private Long productId;
}

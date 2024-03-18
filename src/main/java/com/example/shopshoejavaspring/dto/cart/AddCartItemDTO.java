package com.example.shopshoejavaspring.dto.cart;

import lombok.Data;

@Data
public class AddCartItemDTO {

    private Long userId;

    private Long productId;

    private Long quantity;

    private Long colorId;

    private Long sizeId;

}

package com.example.shopshoejavaspring.dto.cart;

import lombok.Data;

@Data
public class UpdateCartDTO {

    private Long id;
    private Long productId;
    private Long userId;
    private Long quantity;

}

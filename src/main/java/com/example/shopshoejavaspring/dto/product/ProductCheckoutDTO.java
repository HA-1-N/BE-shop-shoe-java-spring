package com.example.shopshoejavaspring.dto.product;

import lombok.Data;

@Data
public class ProductCheckoutDTO {

    private Long id;

    private Long quantity;

    private Long colorId;

    private Long sizeId;

    private Long totalPrice;

}

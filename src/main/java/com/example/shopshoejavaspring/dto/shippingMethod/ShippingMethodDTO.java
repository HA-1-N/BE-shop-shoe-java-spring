package com.example.shopshoejavaspring.dto.shippingMethod;

import lombok.Data;

@Data
public class ShippingMethodDTO {
    private Long id;
    private String method;
    private Double price;
}

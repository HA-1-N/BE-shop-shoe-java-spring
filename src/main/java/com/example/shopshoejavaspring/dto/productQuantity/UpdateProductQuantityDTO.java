package com.example.shopshoejavaspring.dto.productQuantity;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProductQuantityDTO extends CreateProductQuantityDTO {

    private List<Long> productQuantityDeleteImageIds;

}

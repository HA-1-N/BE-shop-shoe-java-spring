package com.example.shopshoejavaspring.dto.productQuantity;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.dto.size.SizeDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductQuantityDTO {

    private Long id;

    private Long quantity;

    private Long status;

    private SizeDTO size;

    private ColorDTO color;

    private List<ProductQuantityImageDTO> productQuantityImages;

}

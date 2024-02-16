package com.example.shopshoejavaspring.dto.productQuantity;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.dto.product.GetIdNameProductDTO;
import com.example.shopshoejavaspring.dto.size.SizeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductQuantityDetailDTO {
    private Long id;

    private Long quantity;

    private Long status;

    private SizeDTO size;

    private ColorDTO color;

    private GetIdNameProductDTO product;

    private List<ProductQuantityImageDTO> productQuantityImages;
}

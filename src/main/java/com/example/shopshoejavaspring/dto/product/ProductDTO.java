package com.example.shopshoejavaspring.dto.product;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.dto.category.CategoryDTO;
import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.dto.productImage.ProductImageDTO;
import com.example.shopshoejavaspring.dto.productQuantity.ProductQuantityDTO;
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
public class ProductDTO {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private Long status;

    private CategoryDTO category;

    private BrandDTO brand;

    private List<ProductImageDTO> productImages;

    private List<ProductQuantityDTO> productQuantities;

}

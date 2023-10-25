package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product>{
    List<ProductDTO> toProductDTOs(List<Product> products);

    ProductDTO toDto(Product product);
}

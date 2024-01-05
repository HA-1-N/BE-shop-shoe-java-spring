package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.productImage.ProductImageDTO;
import com.example.shopshoejavaspring.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductImageMapper extends EntityMapper<ProductImageDTO, ProductImage> {

    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

    @Mapping(source = "image", target = "name")
    ProductImageDTO toDto(ProductImage productImage);

}

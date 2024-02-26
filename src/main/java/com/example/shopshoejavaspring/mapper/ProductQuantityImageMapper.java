package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.productQuantity.ProductQuantityImageDTO;
import com.example.shopshoejavaspring.entity.ProductQuantityImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductQuantityImageMapper extends EntityMapper<ProductQuantityImageDTO, ProductQuantityImage>{

    ProductQuantityMapper INSTANCE = Mappers.getMapper(ProductQuantityMapper.class);

    @Mapping(source = "image", target = "url")
    ProductQuantityImageDTO toDto(ProductQuantityImage productQuantityImage);
}

package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.productQuantity.ProductQuantityDTO;
import com.example.shopshoejavaspring.dto.productQuantity.ProductQuantityDetailDTO;
import com.example.shopshoejavaspring.entity.ProductQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductImageMapper.class, ProductQuantityMapper.class, ProductQuantityImageMapper.class, SizeMapper.class, ColorMapper.class, ProductMapper.class, ProductImageMapper.class, ProductQuantityMapper.class, ProductQuantityImageMapper.class})
public interface ProductQuantityMapper extends EntityMapper<ProductQuantityDetailDTO, ProductQuantity> {
    List<ProductQuantityDetailDTO> toProductQuantityDTOs(List<ProductQuantity> content);

    ProductQuantityDetailDTO toDto(ProductQuantity productQuantity);
}

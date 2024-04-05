package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryProductDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.entity.HotCategory;
import com.example.shopshoejavaspring.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ProductQuantityMapper.class, ProductMapper.class})
public interface HotCategoryProductMapper extends EntityMapper<HotCategoryProductDTO, HotCategory> {

    List<HotCategoryProductDTO> toListDTOs(List<HotCategory> products);

    HotCategoryProductDTO toDto(HotCategory hotCategory);
}

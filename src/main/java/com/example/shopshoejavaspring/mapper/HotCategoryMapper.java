package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryDTO;
import com.example.shopshoejavaspring.entity.HotCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ProductQuantityMapper.class, ProductMapper.class})
public interface HotCategoryMapper extends EntityMapper<HotCategoryDTO, HotCategory> {
    List<HotCategoryDTO> toListDTOs(List<HotCategory> content);
}

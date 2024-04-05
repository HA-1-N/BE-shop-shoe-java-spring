package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.category.CategoryDTO;
import com.example.shopshoejavaspring.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    List<CategoryDTO> toCategoryDTOs(List<Category> categories);

    CategoryDTO toDto(Category category);

}

package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    List<BrandDTO> toBrandDTOs(List<Brand> content);
}

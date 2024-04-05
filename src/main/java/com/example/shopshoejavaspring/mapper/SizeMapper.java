package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.size.SizeDTO;
import com.example.shopshoejavaspring.entity.Size;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SizeMapper extends EntityMapper<SizeDTO, Size>{
}

package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.entity.Color;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper extends EntityMapper<ColorDTO, Color> {

    List<ColorDTO> toColorDTOs(List<Color> colors);

    ColorDTO toDto(Color entity);
}

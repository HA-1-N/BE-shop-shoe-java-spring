package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.entity.Color;
import com.example.shopshoejavaspring.mapper.ColorMapper;
import com.example.shopshoejavaspring.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorService {

    private final ColorRepository colorRepository;

    private final ColorMapper colorMapper;

    public Color createColor(ColorDTO colorDTO) {
        Color color = new Color();
        color.setName(colorDTO.getName());
        color.setCode(colorDTO.getCode());
        return colorRepository.save(color);
    }

    public Page<ColorDTO> filterColor(ColorDTO colorDTO, Pageable pageable) {
        Page<Color> listColor = colorRepository.findByNameContainingAndCodeContaining(colorDTO.getName(), colorDTO.getCode(), pageable);
        List<ColorDTO> colorDTOList = colorMapper.toColorDTOs(listColor.getContent());
        return new PageImpl<>(colorDTOList, pageable, listColor.getTotalElements());
    }

    public ColorDTO updateColor(ColorDTO colorDTO) {
        Color color = colorRepository.findById(colorDTO.getId()).orElseThrow(() -> new RuntimeException("Color not found"));
        color.setName(colorDTO.getName());
        color.setCode(colorDTO.getCode());
        return colorMapper.toDto(colorRepository.save(color));
    }

    public void deleteColor(ColorDTO colorDTO) {
        Color color = colorRepository.findById(colorDTO.getId()).orElseThrow(() -> new RuntimeException("Color not found"));
        colorRepository.delete(color);
    }

    public List<ColorDTO> getAllColor() {
        List<Color> colors = colorRepository.findAll();
        return colorMapper.toColorDTOs(colors);
    }
}

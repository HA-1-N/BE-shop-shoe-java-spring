package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.size.SizeDTO;
import com.example.shopshoejavaspring.entity.Size;
import com.example.shopshoejavaspring.mapper.SizeMapper;
import com.example.shopshoejavaspring.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepository sizeRepository;

    private final SizeMapper sizeMapper;

    public Size createSize(SizeDTO sizeDTO) {
        Size sizeExist = sizeRepository.findByNameContains(sizeDTO.getName());

        if (sizeExist != null) {
            throw new RuntimeException("Size is exist");
        }

        Size size = new Size();
        size.setName(sizeDTO.getName());
        return sizeRepository.save(size);
    }

    public Page<SizeDTO> filterSize(SizeDTO sizeDTO , Pageable pageable) {

        Page<Size> listSize = sizeRepository.findByNameContains(sizeDTO.getName(), pageable);

        List<SizeDTO> sizeDTOList = sizeMapper.toDto(listSize.getContent());

        return new PageImpl<>(sizeDTOList, pageable, listSize.getTotalElements());
    }

    public SizeDTO findById(Long id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
        return sizeMapper.toDto(size);
    }

    public SizeDTO updateSize(Long id, SizeDTO sizeDTO) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
        // check size exist
        Size sizeExist = sizeRepository.findByNameContains(sizeDTO.getName());
        if (sizeExist != null && !sizeExist.getId().equals(id)) {
            throw new RuntimeException("Size is exist");
        }
        size.setName(sizeDTO.getName());
        return sizeMapper.toDto(sizeRepository.save(size));
    }

    public List<SizeDTO> getAllSize() {
        List<Size> sizeList = sizeRepository.findAll();
        return sizeMapper.toDto(sizeList);
    }
}

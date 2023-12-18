package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.entity.Brand;
import com.example.shopshoejavaspring.mapper.BrandMapper;
import com.example.shopshoejavaspring.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor // tự động khởi tạo các biến final từ constructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public Brand createBrand(BrandDTO brandDTO) {
        Brand brand = brandMapper.toEntity(brandDTO);
        brandRepository.save(brand);
        return brand;
    }

    public Page<BrandDTO> filterBrand(BrandDTO brandDTO, Pageable pageable) {
        Page<Brand> brand = brandRepository.findByNameContains(brandDTO.getName(), pageable);
        List<BrandDTO> brandDTOList = brandMapper.toBrandDTOs(brand.getContent());
        return new PageImpl<>(brandDTOList, pageable, brand.getTotalElements());
    }

    public List<BrandDTO> getAllBrand() {
        List<Brand> brand = brandRepository.findAll();
        List<BrandDTO> brandDTOList = brandMapper.toBrandDTOs(brand);
        return brandDTOList;
    }
}

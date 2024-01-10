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
        Page<Brand> brand = brandRepository.filterBrand(brandDTO.getName(), pageable);
        List<BrandDTO> brandDTOList = brandMapper.toBrandDTOs(brand.getContent());
        return new PageImpl<>(brandDTOList, pageable, brand.getTotalElements());
    }

    public List<BrandDTO> getAllBrand() {
        List<Brand> brand = brandRepository.findAll();
        List<BrandDTO> brandDTOList = brandMapper.toBrandDTOs(brand);
        return brandDTOList;
    }

    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        return brandMapper.toDto(brand);
    }

    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

        // check brand exist
        Brand brandExist = brandRepository.findByNameContains(brandDTO.getName());
        if (brandExist != null && !brandExist.getId().equals(id)) {
            throw new RuntimeException("Brand is exist");
        }

        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        return brandMapper.toDto(brandRepository.save(brand));
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        brandRepository.delete(brand);
    }
}

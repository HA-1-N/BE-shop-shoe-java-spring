package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.entity.Brand;
import com.example.shopshoejavaspring.mapper.BrandMapper;
import com.example.shopshoejavaspring.repository.BrandRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor // tự động khởi tạo các biến final từ constructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    private final FileStorageService fileStorageService;

    public Brand createBrand(BrandDTO brandDTO, MultipartFile file) {
        Brand brand = brandMapper.toEntity(brandDTO);
//        Brand brand = new Brand();
//        brand.setName(brandDTO.getName());
//        brand.setDescription(brandDTO.getDescription());
        // set file
        try {
            String fileName = fileStorageService.uploadImage(file);
            brand.setImage(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
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

    public BrandDTO updateBrand(Long id, MultipartFile file, BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

        // check brand exist
        Brand brandExist = brandRepository.findByNameContains(brandDTO.getName());
        if (brandExist != null && !brandExist.getId().equals(id)) {
            throw new RuntimeException("Brand is exist");
        }

        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());

        // set file
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileStorageService.uploadImage(file);
                brand.setImage(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }

        return brandMapper.toDto(brandRepository.save(brand));
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        brandRepository.delete(brand);
    }
}

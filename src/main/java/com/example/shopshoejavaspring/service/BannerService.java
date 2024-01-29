package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.banner.BannerDTO;
import com.example.shopshoejavaspring.dto.banner.CreateBannerDTO;
import com.example.shopshoejavaspring.dto.banner.FilterBannerDTO;
import com.example.shopshoejavaspring.dto.banner.UpdateBannerDTO;
import com.example.shopshoejavaspring.entity.Banner;
import com.example.shopshoejavaspring.mapper.BannerMapper;
import com.example.shopshoejavaspring.repository.BannerRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional // đánh dấu các phương thức của class này sẽ được quản lý bởi Spring
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final BannerMapper bannerMapper;

    private final FileStorageService fileStorageService;

    public Banner create(CreateBannerDTO createBannerDTO, MultipartFile file) {
        Banner banner = new Banner();
        banner.setContent(createBannerDTO.getContent());
        banner.setLink(createBannerDTO.getLink());
        banner.setTitle(createBannerDTO.getTitle());
        // set file
        try {
            String fileName = fileStorageService.uploadImage(file);
            banner.setImage(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

        return bannerRepository.save(banner);
    }

    public Banner update(UpdateBannerDTO updateBannerDTO, MultipartFile file) {
        Banner banner = bannerRepository.findById(updateBannerDTO.getId()).orElseThrow(() -> new RuntimeException("Banner not found"));
        banner.setId(updateBannerDTO.getId());
        banner.setContent(updateBannerDTO.getContent());
        banner.setLink(updateBannerDTO.getLink());
        banner.setTitle(updateBannerDTO.getTitle());

        // set file
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileStorageService.uploadImage(file);
                banner.setImage(fileName);
            } catch (Exception e) {
                // Handle the specific exception, log, and possibly rethrow if needed
                throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
            }
        }

        return bannerRepository.save(banner);
    }

    public Page<BannerDTO> getAll(Pageable pageable) {
        Page<Banner> banners = bannerRepository.findAll(pageable);
        List<BannerDTO> bannerDTOS = bannerMapper.toDTOs(banners.getContent());
        return new PageImpl<>(bannerDTOS, pageable, banners.getTotalElements());
    }

    public BannerDTO getById(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new RuntimeException("Banner not found"));
        return bannerMapper.toDto(banner);
    }

    public void delete(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new RuntimeException("Banner not found"));
        bannerRepository.delete(banner);
    }
}

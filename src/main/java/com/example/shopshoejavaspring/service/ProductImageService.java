package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.productImage.ProductImageDTO;
import com.example.shopshoejavaspring.entity.ProductImage;
import com.example.shopshoejavaspring.mapper.ProductImageMapper;
import com.example.shopshoejavaspring.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final ProductImageMapper productImageMapper;

    public void delete(Long id) {
        productImageRepository.deleteById(id);
    }

    public List<ProductImageDTO> findAll() {
        List<ProductImage> productImage = productImageRepository.findAll();
        return productImageMapper.toDto(productImage);
    }
}

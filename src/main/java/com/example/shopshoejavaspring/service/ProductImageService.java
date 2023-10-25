package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.entity.ProductImage;
import com.example.shopshoejavaspring.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public void delete(Long id) {
        productImageRepository.deleteById(id);
    }
}

package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.repository.ProductQuantityImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQuantityImageService {

    private final ProductQuantityImageRepository productQuantityRepository;

    public void delete(Long id) {
        productQuantityRepository.deleteById(id);
    }

}

package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.productQuantity.CreateProductQuantityDTO;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.repository.ColorRepository;
import com.example.shopshoejavaspring.repository.ProductQuantityRepository;
import com.example.shopshoejavaspring.repository.ProductRepository;
import com.example.shopshoejavaspring.repository.SizeRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;

    public final ProductRepository productRepository;

    private final SizeRepository sizeRepository;

    private final ColorRepository colorRepository;

    private final FileStorageService fileStorageService;

    public ProductQuantity create(CreateProductQuantityDTO createProductQuantityDTO, List<MultipartFile> files) {
        ProductQuantity productQuantity = new ProductQuantity();

        productQuantity.setQuantity(createProductQuantityDTO.getQuantity());
        productQuantity.setStatus(createProductQuantityDTO.getStatus());
        Size size = sizeRepository.findById(createProductQuantityDTO.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
        productQuantity.setSize(size);
        Color color = colorRepository.findById(createProductQuantityDTO.getColorId()).orElseThrow(() -> new RuntimeException("Color not found"));
        productQuantity.setColor(color);
        Product product = productRepository.findById(createProductQuantityDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        productQuantity.setProduct(product);

        List<ProductQuantityImage> productQuantityImages = files.stream().map(file -> {
            String fileName = null;
            try {
                fileName = fileStorageService.uploadImage(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProductQuantityImage productQuantityImage = new ProductQuantityImage();
            productQuantityImage.setProductQuantity(productQuantity);
            productQuantityImage.setImage(fileName);
            return productQuantityImage;
        }).collect(Collectors.toList());
        productQuantity.setProductQuantityImages(productQuantityImages);
        productQuantityRepository.save(productQuantity);
        return productQuantity;
    }
}

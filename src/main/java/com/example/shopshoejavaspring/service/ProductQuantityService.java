package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.productQuantity.*;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.mapper.ProductQuantityMapper;
import com.example.shopshoejavaspring.repository.*;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

    private final ProductQuantityMapper productQuantityMapper;

    @Autowired
    private final ProductQuantityImageService productQuantityImageService;

    public ProductQuantity create(CreateProductQuantityDTO createProductQuantityDTO, List<MultipartFile> files) {
        ProductQuantity productQuantity = new ProductQuantity();

        productQuantity.setQuantity(createProductQuantityDTO.getQuantity());
        productQuantity.setStatus(createProductQuantityDTO.getStatus());

        Size size = sizeRepository.findById(createProductQuantityDTO.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
        Color color = colorRepository.findById(createProductQuantityDTO.getColorId()).orElseThrow(() -> new RuntimeException("Color not found"));

        // check size and color exist in product quantity
        Optional<ProductQuantity> productQuantityOptionalSizeAndColor = productQuantityRepository.findBySizeIdAndColorIdAndProductId(createProductQuantityDTO.getSizeId(), createProductQuantityDTO.getColorId(), createProductQuantityDTO.getProductId());
        if (productQuantityOptionalSizeAndColor.isPresent()) {
            throw new RuntimeException("Size and color exist in product quantity");
        }

        productQuantity.setSize(size);
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


    public Page<ProductQuantityDetailDTO> filter(FilterProductQuantityDTO productQuantityDTO, Pageable pageable) {
        List<ProductQuantityDetailDTO> productQuantityDetailDTOS = new ArrayList<>();
        Page<ProductQuantity> productQuantities = productQuantityRepository.filter(
                productQuantityDTO.getProductId(),
                productQuantityDTO.getColorId(),
                productQuantityDTO.getSizeId(),
                productQuantityDTO.getStatus(),
                pageable);
        productQuantities.forEach(productQuantity -> {
            ProductQuantityDetailDTO productQuantityDetailDTO = productQuantityMapper.toDto(productQuantity);
            productQuantityDetailDTOS.add(productQuantityDetailDTO);
        });
        return new PageImpl<>(productQuantityDetailDTOS, pageable, productQuantities.getTotalElements());
    }

    public List<ProductQuantityDetailDTO> findAll() {
        List<ProductQuantityDetailDTO> productQuantityDetailDTOS = new ArrayList<>();
        List<ProductQuantity> productQuantities = productQuantityRepository.findAll();
        productQuantities.forEach(productQuantity -> {
            ProductQuantityDetailDTO productQuantityDetailDTO = productQuantityMapper.toDto(productQuantity);
            productQuantityDetailDTOS.add(productQuantityDetailDTO);
        });
        return productQuantityDetailDTOS;
    }

    public ProductQuantityDetailDTO findById(Long id) {
        ProductQuantity productQuantity = productQuantityRepository.findById(id).orElseThrow(() -> new RuntimeException("Product quantity not found"));
        ProductQuantityDetailDTO productQuantityDetailDTO = productQuantityMapper.toDto(productQuantity);
        return productQuantityDetailDTO;
    }

    public void delete(Long id) {
        ProductQuantity productQuantity = productQuantityRepository.findById(id).orElseThrow(() -> new RuntimeException("Product quantity not found"));
        productQuantityRepository.delete(productQuantity);
    }

    public ProductQuantity update(UpdateProductQuantityDTO updateProductQuantityDTO, List<MultipartFile> files) {

        ProductQuantity productQuantity = productQuantityRepository.findById(updateProductQuantityDTO.getId()).orElseThrow(() -> new RuntimeException("Product quantity not found"));
        productQuantity.setId(updateProductQuantityDTO.getId());

        productQuantity.setQuantity(updateProductQuantityDTO.getQuantity());
        productQuantity.setStatus(updateProductQuantityDTO.getStatus());

        Size size = sizeRepository.findById(updateProductQuantityDTO.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
        Color color = colorRepository.findById(updateProductQuantityDTO.getColorId()).orElseThrow(() -> new RuntimeException("Color not found"));

        // check size and color exist in product quantity
        Optional<ProductQuantity> productQuantityOptionalSizeAndColor = productQuantityRepository.findBySizeIdAndColorIdAndProductId(updateProductQuantityDTO.getSizeId(), updateProductQuantityDTO.getColorId(), updateProductQuantityDTO.getProductId());
        if (productQuantityOptionalSizeAndColor.isPresent() && !productQuantityOptionalSizeAndColor.get().getId().equals(updateProductQuantityDTO.getId())) {
            throw new RuntimeException("Size and color exist in product quantity");
        }

        productQuantity.setSize(size);
        productQuantity.setColor(color);
        Product product = productRepository.findById(updateProductQuantityDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        productQuantity.setProduct(product);

        // delete product quantity image

        List<ProductQuantityImage> listProductQuantityImage = productQuantity.getProductQuantityImages();

        List<Long> productQuantityDeleteImageIds = updateProductQuantityDTO.getProductQuantityDeleteImageIds();

        Iterator<ProductQuantityImage> productQuantityImageIterator = listProductQuantityImage.iterator();

        while (productQuantityImageIterator.hasNext()) {
            ProductQuantityImage productQuantityImage = productQuantityImageIterator.next();
            if (productQuantityDeleteImageIds.contains(productQuantityImage.getId())) {
                productQuantityImageService.delete(productQuantityImage.getId());
                productQuantityImageIterator.remove();
            }
        }


//        if (files != null && files.size() > 0) {
//            for (MultipartFile file : files) {
//                String fileName = null;
//                try {
//                    fileName = fileStorageService.uploadImage(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ProductQuantityImage productQuantityImage = new ProductQuantityImage();
//                productQuantityImage.setProductQuantity(productQuantity);
//                productQuantityImage.setImage(fileName);
//                listProductQuantityImage.add(productQuantityImage);
//            }
//        }

        if (files != null && files.size() > 0) {
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
            listProductQuantityImage.addAll(productQuantityImages);
        }

        productQuantity.setProductQuantityImages(listProductQuantityImage);
        productQuantityRepository.save(productQuantity);
        return productQuantity;

    }
}

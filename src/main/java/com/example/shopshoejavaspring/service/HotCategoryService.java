package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.hotCategory.AddProductHotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.DeleteProductHotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryProductDTO;
import com.example.shopshoejavaspring.entity.HotCategory;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.mapper.HotCategoryMapper;
import com.example.shopshoejavaspring.mapper.HotCategoryProductMapper;
import com.example.shopshoejavaspring.repository.HotCategoryRepository;
import com.example.shopshoejavaspring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotCategoryService {

    @Autowired
    private final HotCategoryRepository hotCategoryRepository;

    private final HotCategoryMapper hotCategoryMapper;

    private final HotCategoryProductMapper hotCategoryProductMapper;

    @Autowired
    private ProductRepository productRepository;

    public Page<HotCategoryProductDTO> filter(HotCategoryDTO hotCategoryDTO, Pageable pageable) {

        Page<HotCategory> hotCategoryProductDTOPage = hotCategoryRepository.filter(hotCategoryDTO.getId(), pageable);

        List<HotCategoryProductDTO> hotCategoryProductDTOList = hotCategoryProductMapper.toListDTOs(hotCategoryProductDTOPage.getContent());

        return new PageImpl<>(hotCategoryProductDTOList, pageable, hotCategoryProductDTOPage.getTotalElements());
    }

    public Page<HotCategoryDTO> getAll(Pageable pageable) {

        Page<HotCategory> hotCategoryDTOPage = hotCategoryRepository.findAll(pageable);

        List<HotCategoryDTO> hotCategoryDTOList = hotCategoryMapper.toListDTOs(hotCategoryDTOPage.getContent());

        return new PageImpl<>(hotCategoryDTOList, pageable, hotCategoryDTOPage.getTotalElements());

    }

    public HotCategoryProductDTO findById(Long id) {
        HotCategory hotCategory = hotCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found HotCategory"));
        return hotCategoryProductMapper.toDto(hotCategory);
    }

    public HotCategory create(HotCategoryDTO hotCategoryDTO) {
        HotCategory hotCategory = hotCategoryMapper.toEntity(hotCategoryDTO);
        return hotCategoryRepository.save(hotCategory);
    }

    public void addProductHotCategory(AddProductHotCategoryDTO hotCategoryProductIds, Long id) {
        HotCategory hotCategory = hotCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HotCategory not found with id: " + id));

        List<Product> products = productRepository.findAllById(hotCategoryProductIds.getProductIds());

        if (products.isEmpty()) {
            throw new RuntimeException("No products found with the provided IDs.");
        }

        hotCategory.getProducts().addAll(products);
        hotCategoryRepository.save(hotCategory);

    }

    public void deleteProductHotCategory(DeleteProductHotCategoryDTO hotCategoryProductId, Long id) {

        HotCategory hotCategory = hotCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HotCategory not found with id: " + id));

        Product product = productRepository.findById(hotCategoryProductId.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + hotCategoryProductId.getProductId()));

        hotCategory.getProducts().remove(product);
        hotCategoryRepository.save(hotCategory);

    }
}

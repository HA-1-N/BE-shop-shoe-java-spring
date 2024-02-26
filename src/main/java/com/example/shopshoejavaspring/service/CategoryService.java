package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.category.CategoryDTO;
import com.example.shopshoejavaspring.entity.Category;
import com.example.shopshoejavaspring.mapper.CategoryMapper;
import com.example.shopshoejavaspring.repository.CategoryRepository;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final FileStorageService fileStorageService;

    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDTOs(categories);
    }

    public Category createCategory(CategoryDTO categoryDTO, MultipartFile file) {
        Category category = categoryMapper.toEntity(categoryDTO);

        // set file
        try {
            String fileName = fileStorageService.uploadImage(file);
            category.setImage(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

        return categoryRepository.save(category);
    }

    public CategoryDTO updateCategory(MultipartFile file, CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        // check category name
        if (!category.getName().equals(categoryDTO.getName())) {
            if (categoryRepository.existsByName(categoryDTO.getName())) {
                throw new RuntimeException("Category name is already exists");
            }
        }

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // set file
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileStorageService.uploadImage(file);
                category.setImage(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public Page<CategoryDTO> filterCategory(CategoryDTO categoryDTO, Pageable pageable) {
        Page<Category> listCategory = categoryRepository.findByNameContaining(categoryDTO.getName(), pageable);
        List<CategoryDTO> categoryDTOList = categoryMapper.toCategoryDTOs(listCategory.getContent());
        return new PageImpl<>(categoryDTOList, pageable, listCategory.getTotalElements());
    }

    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return categoryMapper.toDto(category);
    }

    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }
}

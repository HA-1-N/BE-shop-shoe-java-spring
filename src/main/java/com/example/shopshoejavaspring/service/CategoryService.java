package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.category.CategoryDTO;
import com.example.shopshoejavaspring.entity.Category;
import com.example.shopshoejavaspring.mapper.CategoryMapper;
import com.example.shopshoejavaspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDTOs(categories);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public Page<CategoryDTO> filterCategory(CategoryDTO categoryDTO, Pageable pageable) {
        Page<Category> listCategory = categoryRepository.findByNameContainingAndDescriptionContaining(categoryDTO.getName(), categoryDTO.getDescription(), pageable);
        List<CategoryDTO> categoryDTOList = categoryMapper.toCategoryDTOs(listCategory.getContent());
        return new PageImpl<>(categoryDTOList, pageable, listCategory.getTotalElements());
    }

    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return categoryMapper.toDto(category);
    }
}

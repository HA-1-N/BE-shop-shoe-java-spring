package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.category.CategoryDTO;
import com.example.shopshoejavaspring.entity.Category;
import com.example.shopshoejavaspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryResource {

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        log.debug("REST request to get all Category");
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestParam(value = "file", required = false) MultipartFile file, @Valid @RequestPart("data") CategoryDTO categoryDTO) {
        log.debug("REST request to create Category : {}", categoryDTO);
        Category category = categoryService.createCategory(categoryDTO, file);
        categoryDTO.setId(category.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CategoryDTO>> filterCategory(@Valid @RequestBody CategoryDTO categoryDTO, Pageable pageable) {
        log.debug("REST request to filter Category : {}", categoryDTO);
        Page<CategoryDTO> listCategory = categoryService.filterCategory(categoryDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(listCategory.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(listCategory.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(listCategory.getNumber()));
        headers.add("X-Page-Size", String.valueOf(listCategory.getSize()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(listCategory.getContent());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestParam(value = "file", required = false) MultipartFile file ,@Valid @RequestPart("data") CategoryDTO categoryDTO, @PathVariable Long id) {
        log.debug("REST request to update Category : {}", categoryDTO);
        CategoryDTO category = categoryService.updateCategory(file, categoryDTO, id);
        categoryDTO.setId(category.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        CategoryDTO categoryDTO = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }
}

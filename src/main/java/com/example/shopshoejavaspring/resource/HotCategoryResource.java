package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.hotCategory.AddProductHotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.DeleteProductHotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryDTO;
import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryProductDTO;
import com.example.shopshoejavaspring.entity.HotCategory;
import com.example.shopshoejavaspring.service.HotCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hot-category")
@Slf4j
@RequiredArgsConstructor
public class HotCategoryResource {

    private final HotCategoryService hotCategoryService;

    @PostMapping("/create")
    public ResponseEntity<HotCategoryDTO> create(@RequestBody HotCategoryDTO hotCategoryDTO) {
        log.debug("REST request to create HotCategory: {}", hotCategoryDTO);
        HotCategory hotCategory = hotCategoryService.create(hotCategoryDTO);
        hotCategoryDTO.setId(hotCategory.getId());
        return ResponseEntity.ok().body(hotCategoryDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<HotCategoryProductDTO>> filter(@RequestBody HotCategoryDTO hotCategoryDTO, Pageable pageable) {
        log.debug("REST request to filter HotCategory by name: {}", hotCategoryDTO);
        Page<HotCategoryProductDTO> hotCategoryProductDTOPage = hotCategoryService.filter(hotCategoryDTO, pageable);
        return ResponseEntity.ok().body(hotCategoryProductDTOPage.getContent());

    }

    @GetMapping("/get-all")
    public ResponseEntity<List<HotCategoryDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all HotCategory");

        Page<HotCategoryDTO> hotCategoryProductDTOPage = hotCategoryService.getAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(hotCategoryProductDTOPage.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(hotCategoryProductDTOPage.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(hotCategoryProductDTOPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(hotCategoryProductDTOPage.getSize()));

        return ResponseEntity.ok().headers(headers).body(hotCategoryProductDTOPage.getContent());

    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<HotCategoryProductDTO> findById(@PathVariable Long id) {
        log.debug("REST request to find HotCategory by id : {}", id);
        HotCategoryProductDTO hotCategoryProductDTO = hotCategoryService.findById(id);
        return ResponseEntity.ok().body(hotCategoryProductDTO);
    }

    @PostMapping("/add-product-hot-category/{id}")
    public ResponseEntity<String> addProductHotCategory(@PathVariable Long id , @RequestBody AddProductHotCategoryDTO hotCategoryProductIds) {
        log.debug("REST request to add product to HotCategory : {}", hotCategoryProductIds);
        hotCategoryService.addProductHotCategory(hotCategoryProductIds, id);
        return ResponseEntity.ok().body("Add product to HotCategory success");
    }

    @PostMapping("/delete-product-hot-category/{id}")
    public ResponseEntity<String> deleteProductHotCategory(@PathVariable Long id , @RequestBody DeleteProductHotCategoryDTO hotCategoryProductId) {
        log.debug("REST request to delete product to HotCategory : {}", hotCategoryProductId);
        hotCategoryService.deleteProductHotCategory(hotCategoryProductId, id);
        return ResponseEntity.ok().body("Delete product to HotCategory success");
    }

}

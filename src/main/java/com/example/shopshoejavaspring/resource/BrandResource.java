package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.entity.Brand;
import com.example.shopshoejavaspring.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
@Slf4j
@RequiredArgsConstructor
public class BrandResource {

    private final BrandService brandService;

    @PostMapping("/create")
    public ResponseEntity<BrandDTO> createBrand(@Valid @RequestBody BrandDTO brandDTO) {
        log.info("REST request to save Brand : {}", brandDTO);
        Brand brand = brandService.createBrand(brandDTO);
        brandDTO.setId(brand.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(brandDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<BrandDTO>> filterBrand(@RequestBody BrandDTO brandDTO, Pageable pageable) {
        log.info("REST request to filter Brand : {}", brandDTO);
        Page<BrandDTO> brand = brandService.filterBrand(brandDTO, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(brand.getContent());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BrandDTO>> getAllBrand() {
        log.info("REST request to get all Brand");
        List<BrandDTO> brand = brandService.getAllBrand();
        return ResponseEntity.status(HttpStatus.OK).body(brand);
    }

}

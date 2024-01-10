package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.brand.BrandDTO;
import com.example.shopshoejavaspring.entity.Brand;
import com.example.shopshoejavaspring.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(brand.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(brand.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(brand.getNumber()));
        headers.add("X-Page-Size", String.valueOf(brand.getSize()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(brand.getContent());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BrandDTO>> getAllBrand() {
        log.info("REST request to get all Brand");
        List<BrandDTO> brand = brandService.getAllBrand();
        return ResponseEntity.status(HttpStatus.OK).body(brand);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<BrandDTO> findById(@PathVariable Long id) {
        log.info("REST request to find Brand by id : {}", id);
        BrandDTO brandDTO = brandService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(brandDTO);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandDTO brandDTO) {
        log.info("REST request to update Brand : {}", brandDTO);
        BrandDTO brandDTO1 = brandService.updateBrand(id, brandDTO);
        return ResponseEntity.status(HttpStatus.OK).body(brandDTO1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        log.info("REST request to delete Brand by id : {}", id);
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success");
    }

}

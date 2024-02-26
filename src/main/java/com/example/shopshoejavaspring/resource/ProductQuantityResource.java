package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.productQuantity.*;
import com.example.shopshoejavaspring.entity.ProductQuantity;
import com.example.shopshoejavaspring.service.ProductQuantityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-quantity")
@Slf4j
@RequiredArgsConstructor
public class ProductQuantityResource {

    private final ProductQuantityService productQuantityService;

    @PostMapping("/create")
    public ResponseEntity<CreateProductQuantityDTO> createProductQuantity(@RequestParam(value = "files", required = false) List<MultipartFile> files, @Valid @RequestPart("data") CreateProductQuantityDTO createProductQuantityDTO) {
        log.debug("REST request to save ProductQuantity");
        ProductQuantity productQuantity = productQuantityService.create(createProductQuantityDTO, files);
        createProductQuantityDTO.setId(productQuantity.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductQuantityDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateProductQuantityDTO> updateProductQuantity(@RequestParam(value = "files", required = false) List<MultipartFile> files, @Valid @RequestPart("data") UpdateProductQuantityDTO updateProductQuantityDTO) {
        log.debug("REST request to update ProductQuantity");
        ProductQuantity productQuantity = productQuantityService.update(updateProductQuantityDTO, files);
        return ResponseEntity.status(HttpStatus.OK).body(updateProductQuantityDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductQuantityDetailDTO>> filterProductQuantity(@RequestBody FilterProductQuantityDTO productQuantityDTO, Pageable pageable) {
        log.debug("REST request to filter ProductQuantity");
        Page<ProductQuantityDetailDTO> productQuantities = productQuantityService.filter(productQuantityDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(productQuantities.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(productQuantities.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(productQuantities.getNumber()));
        headers.add("X-Page-Size", String.valueOf(productQuantities.getSize()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(productQuantities.getContent());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ProductQuantityDetailDTO> getProductQuantityById(@PathVariable Long id) {
        log.debug("REST request to get ProductQuantity : {}", id);
        ProductQuantityDetailDTO productQuantityDTO = productQuantityService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productQuantityDTO);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ProductQuantityDetailDTO>> findAll() {
        log.debug("REST request to get all ProductQuantity");
        List<ProductQuantityDetailDTO> productQuantityDTO = productQuantityService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productQuantityDTO);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductQuantity(@PathVariable Long id) {
        log.debug("REST request to delete ProductQuantity : {}", id);
        productQuantityService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

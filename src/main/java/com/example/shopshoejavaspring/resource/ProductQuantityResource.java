package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.productQuantity.CreateProductQuantityDTO;
import com.example.shopshoejavaspring.entity.ProductQuantity;
import com.example.shopshoejavaspring.service.ProductQuantityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}

package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.productImage.ProductImageDTO;
import com.example.shopshoejavaspring.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product-image")
public class ProductImageResource {

    private final ProductImageService productImageService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductImageDTO>> getAllProductImage() {
        log.debug("BEGIN - /api/product-image/get-all");
        List<ProductImageDTO> productImageDTOList = productImageService.findAll();
        log.debug("END - /api/product-image/get-all");
        return ResponseEntity.ok().body(productImageDTOList);
    }

}

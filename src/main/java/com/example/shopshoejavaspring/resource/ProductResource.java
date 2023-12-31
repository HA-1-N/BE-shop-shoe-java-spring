package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.product.CreateProductDTO;
import com.example.shopshoejavaspring.dto.product.FilterProductDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.dto.product.UpdateProductDTO;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CreateProductDTO> createProduct(@RequestParam(value = "files", required = false) List<MultipartFile> files, @Valid @RequestPart("data") CreateProductDTO data) throws IOException {
        log.debug("BEGIN - /api/product/create");
        Product product = productService.create(data, files);
        data.setId(product.getId());
        log.debug("END - /api/product/create");
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductDTO>> filterProduct(@RequestBody FilterProductDTO filterProductDTO, Pageable pageable) throws IOException {
        log.debug("BEGIN - /api/product/filter");
        Page<ProductDTO> listProduct = productService.filter(filterProductDTO, pageable);
        log.debug("END - /api/product/filter");
        return ResponseEntity.status(HttpStatus.OK).body(listProduct.getContent());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws IOException {
        log.debug("BEGIN - /api/product/get-by-id/" + id);
        ProductDTO productDTO = productService.findById(id);
        log.debug("END - /api/product/get-by-id/" + id);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateProductDTO> updateProduct(@RequestParam(value = "files", required = false) List<MultipartFile> files, @Valid @RequestPart("data") UpdateProductDTO data) throws IOException {
        log.debug("BEGIN - /api/product/update");
        Product product = productService.update(data, files);
        log.debug("END - /api/product/update");
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws IOException {
        log.debug("BEGIN - /api/product/delete/" + id);
        productService.delete(id);
        log.debug("END - /api/product/delete/" + id);
        return ResponseEntity.ok().body("Delete success");
    }
}

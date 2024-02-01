package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.product.*;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.service.ProductService;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(listProduct.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(listProduct.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(listProduct.getNumber()));
        headers.add("X-Page-Size", String.valueOf(listProduct.getSize()));
        log.debug("END - /api/product/filter");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(listProduct.getContent());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws IOException {
        log.debug("BEGIN - /api/product/get-by-id/" + id);
        ProductDTO productDTO = productService.findById(id);
        log.debug("END - /api/product/get-by-id/" + id);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDTO>> getAllProduct() throws IOException {
        log.debug("BEGIN - /api/product/get-all");
        List<ProductDTO> listProductDTO = productService.findAll();
        log.debug("END - /api/product/get-all");
        return ResponseEntity.status(HttpStatus.OK).body(listProductDTO);
    }

    @GetMapping("/get-id-name")
    public ResponseEntity<List<GetIdNameProductDTO>> getIdNameProduct() throws IOException {
        log.debug("BEGIN - /api/product/get-id-name");
        List<GetIdNameProductDTO> listProductDTO = productService.findIdName();
        log.debug("END - /api/product/get-id-name");
        return ResponseEntity.status(HttpStatus.OK).body(listProductDTO);
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

    @GetMapping("/get-product-by-hot-category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductByHotCategory(@PathVariable Long id, Pageable pageable) throws IOException {
        log.debug("BEGIN - /api/product/get-product-by-category/" + id);
        List<ProductDTO> listProductDTO = productService.findByHotCategory(id, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(listProductDTO.size()));
        headers.add("X-Total-Pages", String.valueOf(1));
        headers.add("X-Page-Number", String.valueOf(0));
        headers.add("X-Page-Size", String.valueOf(listProductDTO.size()));
        log.debug("END - /api/product/get-product-by-category/" + id);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(listProductDTO);
    }
}

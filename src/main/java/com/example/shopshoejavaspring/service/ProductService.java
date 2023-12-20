package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.product.CreateProductDTO;
import com.example.shopshoejavaspring.dto.product.FilterProductDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.dto.product.UpdateProductDTO;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.mapper.ProductMapper;
import com.example.shopshoejavaspring.repository.*;
import com.example.shopshoejavaspring.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service // Đánh dấu đây là 1 bean
@RequiredArgsConstructor // Inject bean
@Transactional // Đảm bảo tính toàn vẹn dữ liệu
public class ProductService {

    //    @Autowired // Inject bean
    private final ProductRepository productRepository;

    //    @Autowired
    private final CategoryRepository categoryRepository;

    //    @Autowired
    private final BrandRepository brandRepository;

    //    @Autowired
    private final SizeRepository sizeRepository;

    //    @Autowired
    private final ColorRepository colorRepository;

    //    @Autowired
    private final FileStorageService fileStorageService;

    private final ProductMapper productMapper;

    private final ProductImageService productImageService;

    public Product create(CreateProductDTO data, List<MultipartFile> files) throws IOException {

        Product product = new Product();

        // check product name
        Product checkProductName = productRepository.findByNameContains(data.getName());

        if (checkProductName != null) {
            throw new RuntimeException("Product name is exist");
        } else {
            product.setName(data.getName());
        }

        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setStatus(data.getStatus());

        Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id " + data.getCategoryId()));

        Brand brand = brandRepository.findById(data.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setCategory(category);
        product.setBrand(brand);

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = fileStorageService.uploadImage(file);

            ProductImage productImage = new ProductImage();
            productImage.setImage(imageUrl);
            productImage.setProduct(product);
            productImages.add(productImage);
        }

        product.setProductImages(productImages);
        productRepository.save(product);

        return product;
    }

    public Page<ProductDTO> filter(FilterProductDTO filterProductDTO, Pageable pageable) {
        Page<Product> products = productRepository.filter(filterProductDTO.getName(),
                filterProductDTO.getStatus(),
                filterProductDTO.getCategoryId(),
                filterProductDTO.getBrandId(),
                filterProductDTO.getSizeId(),
                filterProductDTO.getColorId(),
                filterProductDTO.getMinPrice(),
                filterProductDTO.getMaxPrice(),
                pageable);
        List<ProductDTO> productDTO = productMapper.toProductDTOs(products.getContent());
        return new PageImpl<>(productDTO, pageable, products.getTotalElements());
    }

    public ProductDTO findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return productMapper.toDto(product.get());
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public Product update(UpdateProductDTO data, List<MultipartFile> files) throws IOException {
        Product product = productRepository.findById(data.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setId(data.getId());
        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setStatus(data.getStatus());

        Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id " + data.getCategoryId()));

        Brand brand = brandRepository.findById(data.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setCategory(category);
        product.setBrand(brand);

        List<ProductImage> productImages = product.getProductImages();

        List<Long> productDeleteImageIds = data.getProductDeleteImageIds();

        Iterator<ProductImage> iterator = productImages.iterator();

        while (iterator.hasNext()) {
            ProductImage productImage = iterator.next();
            if (productDeleteImageIds.contains(productImage.getId())) {
                productImageService.delete(productImage.getId());
                iterator.remove();
            }
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = fileStorageService.uploadImage(file);

                ProductImage productImage = new ProductImage();
                productImage.setImage(imageUrl);
                productImage.setProduct(product);
                productImages.add(productImage);
            }
        }

        product.setProductImages(productImages);
        productRepository.save(product);

        return product;
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductDTOs(products);
    }
}

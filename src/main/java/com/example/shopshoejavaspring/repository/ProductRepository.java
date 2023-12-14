package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.product.FilterProductDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.* from product p " +
            "where (:name is null or p.name like concat('%', :name, '%')) " +
            "and (:status is null or p.status = :status) " +
            "and (:categoryId is null or p.category_id = :categoryId) " +
            "and (:brandId is null or p.brand_id = :brandId) " +
            "and ( coalesce(null, :sizeId) is null or p.size_id in (:sizeId) ) " +
            "and ( coalesce(null, :colorId) is null or p.color_id in (:colorId) ) " +
            "and (:minPrice is null or p.price >= :minPrice) " +
            "and (:maxPrice is null or p.price <= :maxPrice) " +
            "group by p.id " +
            "order by p.name asc ",
            nativeQuery = true)
    Page<Product> filter(@Param("name") String name,
            @Param("status") Long status,
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("sizeId") List<Long> sizeId,
            @Param("colorId") List<Long> colorId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable);
}

package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.productQuantity.FilterProductQuantityDTO;
import com.example.shopshoejavaspring.entity.ProductQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {

    @Query(value = "select * from product_quantity pq " +
            "where (:productIds is null or pq.product_id in (:productIds)) " +
            "and (:colorIds is null or pq.color_id in (:colorIds)) " +
            "and (:sizeIds is null or pq.size_id in (:sizeIds)) " +
            "and (:status is null or pq.status = :status) " +
            "group by pq.id " +
            "order by pq.id asc ",
            nativeQuery = true)
    Page<ProductQuantity> filter(
            @Param("productIds") List<Long> productId,
            @Param("colorIds") List<Long> colorId,
            @Param("sizeIds") List<Long> sizeId,
            @Param("status") Long status,
            Pageable pageable
    );
}

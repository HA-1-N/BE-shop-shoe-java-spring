package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
}

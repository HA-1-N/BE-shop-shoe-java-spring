package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.ProductQuantityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuantityImageRepository extends JpaRepository<ProductQuantityImage, Long>{
}

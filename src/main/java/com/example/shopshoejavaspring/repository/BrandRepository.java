package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}

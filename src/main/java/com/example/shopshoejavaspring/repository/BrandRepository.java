package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {


    Page<Brand> findByNameContains(String name, Pageable pageable);
}

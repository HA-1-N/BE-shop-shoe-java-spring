package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingAndDescriptionContaining(String name, String description, Pageable pageable);
}

package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

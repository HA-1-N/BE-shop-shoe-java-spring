package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.hotCategory.HotCategoryProductDTO;
import com.example.shopshoejavaspring.entity.HotCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotCategoryRepository extends JpaRepository<HotCategory, Long> {

    @Query(value = "SELECT * FROM hot_category hc WHERE ( :id is null or hc.id = :id ) ", nativeQuery = true)
    Page<HotCategory> filter(@Param("id") Long id, Pageable pageable);
}

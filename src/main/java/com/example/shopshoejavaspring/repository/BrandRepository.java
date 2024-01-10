package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "select b.* from brand b where (:name is null or b.name like concat('%', :name, '%')) order by b.id asc ",
            nativeQuery = true)
    Page<Brand> filterBrand(@Param("name") String name, Pageable pageable);

    Brand findByNameContains(String name);
}

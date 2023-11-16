package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Set<Size> findAllByIdIn(@Param("id") List<Long> id);

    Size findByNameContains(String name);

    Page<Size> findByNameContains(String name, Pageable pageable);
}

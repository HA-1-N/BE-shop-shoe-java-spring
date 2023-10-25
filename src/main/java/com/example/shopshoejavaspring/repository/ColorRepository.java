package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Set<Color> findAllByIdIn( @Param("id") List<Long> colorId);
}

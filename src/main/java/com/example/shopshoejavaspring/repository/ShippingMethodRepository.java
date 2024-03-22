package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.ShippingMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {
    Page<ShippingMethod> findShippingMethodByMethodContaining(String name, Pageable pageable);
}

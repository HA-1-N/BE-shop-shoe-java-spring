package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}

package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query(value = "SELECT sum (op.quantity) FROM OrderProduct op")
    Long getTotalQuantityOrderProduct();
}

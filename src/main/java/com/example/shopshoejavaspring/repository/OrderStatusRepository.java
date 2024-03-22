package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Page<OrderStatus> findOrderStatusByStatusContaining(String status, Pageable pageable);
}

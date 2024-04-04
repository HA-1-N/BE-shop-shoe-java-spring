package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByUserId(Long userId);

    @Query(value = "select * from orders where user_id = :userId", nativeQuery = true)
    Order findOrders(@Param("userId") Long userId);
}

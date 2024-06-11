package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId, Sort sort);

    @Query(value = "select * from orders os where (:orderDate is null or os.order_date = :orderDate) order by os.order_date DESC ", nativeQuery = true)
    Page<Order> filter(@Param("orderDate") Date orderDate, Pageable pageable);

    @Query(value = "select sum(order_total) from orders", nativeQuery = true)
    Double getTotalRevenue();
}

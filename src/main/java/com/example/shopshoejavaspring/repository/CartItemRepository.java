package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "select * from cart_item ci" +
            " left join cart c on c.id = ci.cart_id" +
            " left join user u on u.id = c.user_id" +
            " where (u.id = :userId) ",
            nativeQuery = true)
    List<CartItem> findByCartUserId(@Param("userId") Long userId);

    Integer countByCartUserId(Long userId);
}

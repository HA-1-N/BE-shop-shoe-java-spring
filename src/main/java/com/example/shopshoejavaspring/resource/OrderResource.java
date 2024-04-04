package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.entity.Order;
import com.example.shopshoejavaspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderCheckoutDTO> checkout (@RequestBody OrderCheckoutDTO orderCheckoutDTO) {
        log.info("OrderCheckoutDTO: {}", orderCheckoutDTO);
        orderService.checkout(orderCheckoutDTO);
        return ResponseEntity.status(201).body(orderCheckoutDTO);
    }

    @GetMapping("/get-order-by-user-id/{userId}")
    public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId) {
        log.info("REST request to get order by user id : {}", userId);
        Order order = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok().body(order);
    }

}

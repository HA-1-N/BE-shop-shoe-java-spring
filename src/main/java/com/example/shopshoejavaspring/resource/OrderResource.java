package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.dto.order.OrderDTO;
import com.example.shopshoejavaspring.entity.Order;
import com.example.shopshoejavaspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderCheckoutDTO> checkout(@RequestBody OrderCheckoutDTO orderCheckoutDTO) {
        log.info("OrderCheckoutDTO: {}", orderCheckoutDTO);
        orderService.checkout(orderCheckoutDTO);
        return ResponseEntity.status(201).body(orderCheckoutDTO);
    }

    @GetMapping("/get-order-by-user-id/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderByUserId(@PathVariable Long userId) {
        log.info("REST request to get order by user id : {}", userId);
        List<OrderDTO> orders = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("REST request to get order by id : {}", orderId);
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(order);
    }

}

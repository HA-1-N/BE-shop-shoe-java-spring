package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.order.FilterOrderDTO;
import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.dto.order.OrderDTO;
import com.example.shopshoejavaspring.dto.order.UpdateOrderDTO;
import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.entity.Order;
import com.example.shopshoejavaspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @PostMapping("/filter")
    public ResponseEntity<List<OrderDTO>> filter(@RequestBody FilterOrderDTO orderDTO, Pageable pageable) {
        log.info("REST request to filter order by: {}", orderDTO);
        Page<OrderDTO> orders = orderService.filter(orderDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(orders.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(orders.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(orders.getNumber()));
        headers.add("X-Page-Size", String.valueOf(orders.getSize()));
        return ResponseEntity.ok().headers(headers).body(orders.getContent());
    }

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

    @PostMapping("/change-order-status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@RequestBody UpdateOrderDTO updateOrderDTO) {
        log.info("REST request to update order status : {}", updateOrderDTO);
        OrderDTO order = orderService.changeOrderStatus(updateOrderDTO);
        return ResponseEntity.ok().body(order);
    }

}

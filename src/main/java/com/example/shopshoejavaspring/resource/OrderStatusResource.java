package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.entity.OrderStatus;
import com.example.shopshoejavaspring.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order-status")
@RequiredArgsConstructor
@Slf4j
public class OrderStatusResource {

    private final OrderStatusService orderStatusService;

    @PostMapping("/create")
    public ResponseEntity<OrderStatusDTO> createOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
        log.debug("REST request to save OrderStatus : {}", orderStatusDTO);
        OrderStatus orderStatus = orderStatusService.createOrderStatus(orderStatusDTO);
        orderStatusDTO.setId(orderStatus.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderStatusDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<OrderStatusDTO>> filterOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO, Pageable pageable) {
        log.debug("REST request to filter OrderStatus : {}", orderStatusDTO);
        Page<OrderStatusDTO> page = orderStatusService.filterOrderStatus(orderStatusDTO, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(page.getTotalPages()));
        headers.add("X-Page-Number", String.valueOf(page.getNumber()));
        headers.add("X-Page-Size", String.valueOf(page.getSize()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(page.getContent());
    }
}

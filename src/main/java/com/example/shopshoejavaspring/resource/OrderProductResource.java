package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order-product")
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderProductResource {

    private final OrderProductService orderProductService;

    // api get count quantity of order product
    @GetMapping("/total-quantity")
    public Long getTotalQuantityOrderProduct() {
        log.info("get total quantity of order product");
        Long total = orderProductService.getTotalQuantityOrderProduct();
        return total;
    }

}

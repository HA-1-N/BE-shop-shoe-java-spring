package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public Long getTotalQuantityOrderProduct() {
        Long total = orderProductRepository.getTotalQuantityOrderProduct();
        return total;
    }
}

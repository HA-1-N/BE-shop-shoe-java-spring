package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.entity.OrderStatus;
import com.example.shopshoejavaspring.mapper.OrderStatusMapper;
import com.example.shopshoejavaspring.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusMapper orderStatusMapper;

    public OrderStatus createOrderStatus(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = orderStatusMapper.toEntity(orderStatusDTO);
        orderStatus = orderStatusRepository.save(orderStatus);
        return orderStatus;
    }

    public Page<OrderStatusDTO> filterOrderStatus(OrderStatusDTO orderStatusDTO, Pageable pageable) {
        Page<OrderStatus> orderStatus = orderStatusRepository.findOrderStatusByStatusContaining(orderStatusDTO.getStatus(), pageable);
        List<OrderStatusDTO> orderStatusDTOS = orderStatusMapper.toDto(orderStatus.getContent());
        return new PageImpl<>(orderStatusDTOS, pageable, orderStatus.getTotalElements());
    }

    public List<OrderStatusDTO> getAllOrderStatus() {
        List<OrderStatus> orderStatus = orderStatusRepository.findAll();
        return orderStatusMapper.toDto(orderStatus);
    }
}

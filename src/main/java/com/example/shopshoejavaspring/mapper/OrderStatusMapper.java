package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.entity.OrderStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper extends EntityMapper<OrderStatusDTO, OrderStatus> {
}

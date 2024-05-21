package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.order.OrderDTO;
import com.example.shopshoejavaspring.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ProductMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    List<OrderDTO> toOrderDTOs(List<Order> orders);

    OrderDTO toDto(Order order);
}

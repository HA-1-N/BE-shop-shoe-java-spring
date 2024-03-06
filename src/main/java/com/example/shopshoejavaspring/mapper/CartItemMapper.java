package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.cart.CartItemDTO;
import com.example.shopshoejavaspring.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, ColorMapper.class, SizeMapper.class})
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
    CartItemDTO toDto(CartItem cartItem);

    List<CartItemDTO> toDtos(List<CartItem> cartItems);
}

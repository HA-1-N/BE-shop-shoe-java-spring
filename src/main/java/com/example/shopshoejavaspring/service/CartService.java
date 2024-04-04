package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.cart.AddCartItemDTO;
import com.example.shopshoejavaspring.dto.cart.CartItemDTO;
import com.example.shopshoejavaspring.dto.cart.RemoveFromCartDTO;
import com.example.shopshoejavaspring.entity.Cart;
import com.example.shopshoejavaspring.entity.CartItem;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.entity.User;
import com.example.shopshoejavaspring.mapper.CartItemMapper;
import com.example.shopshoejavaspring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartItemMapper cartItemMapper;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

    private final ProductImageRepository productImageRepository;

    public AddCartItemDTO addToCart(AddCartItemDTO addCartItemDTO) {

        User user = userRepository.findById(addCartItemDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCarts().stream().findFirst().orElse(new Cart());
        if (cart.getUser() == null) {
            cart.setUser(user);
            cartRepository.save(cart); // Lưu giỏ hàng mới vào cơ sở dữ liệu
        }

        CartItem cartItem = new CartItem();

        Product product = productRepository.findById(addCartItemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(addCartItemDTO.getQuantity());
        cartItem.setColorId(addCartItemDTO.getColorId());
        cartItem.setSizeId(addCartItemDTO.getSizeId());
        cartItemRepository.save(cartItem);
        return addCartItemDTO;
    }

    public void removeFromCart(RemoveFromCartDTO removeFromCartDTO) {
        cartItemRepository.deleteById(removeFromCartDTO.getCartItemId());
    }

    public List<CartItemDTO> getCartByUserId(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByCartUserId(userId);
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setCartId(cartItem.getCart().getId());
            cartItemDTO.setProduct(cartItem.getProduct());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setColor(colorRepository.findById(cartItem.getColorId()).orElse(null));
            cartItemDTO.setSize(sizeRepository.findById(cartItem.getSizeId()).orElse(null));
            cartItemDTO.setProductImage(cartItem.getProduct().getProductImages().stream().findFirst().orElse(null));
            cartItemDTOS.add(cartItemDTO);
        }
        return cartItemDTOS;
    }

    public Integer getCountCart(Long userId) {
        return cartItemRepository.countByCartUserId(userId);
    }

    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByCartUserId(userId);
        cartItemRepository.deleteAll(cartItems);
    }
}

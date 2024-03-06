package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.cart.AddCartItemDTO;
import com.example.shopshoejavaspring.dto.cart.CartItemDTO;
import com.example.shopshoejavaspring.dto.cart.RemoveFromCartDTO;
import com.example.shopshoejavaspring.entity.Cart;
import com.example.shopshoejavaspring.entity.CartItem;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.mapper.CartItemMapper;
import com.example.shopshoejavaspring.repository.CartItemRepository;
import com.example.shopshoejavaspring.repository.CartRepository;
import com.example.shopshoejavaspring.repository.ProductRepository;
import com.example.shopshoejavaspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartItemMapper cartItemMapper;

    public AddCartItemDTO addToCart(AddCartItemDTO addCartItemDTO) {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();

        if (addCartItemDTO.getCartId() == null) {
            cart.setUser(userRepository.findById(addCartItemDTO.getUserId()).get());
            cartRepository.save(cart);
        } else {
           cartRepository.findById(addCartItemDTO.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        }

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
        List<CartItemDTO> cartItemDTOS = cartItemMapper.toDtos(cartItems);
        return cartItemDTOS;
    }
}

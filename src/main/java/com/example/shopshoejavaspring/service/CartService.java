package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.cart.AddCartItemDTO;
import com.example.shopshoejavaspring.dto.cart.CartItemDTO;
import com.example.shopshoejavaspring.dto.cart.RemoveFromCartDTO;
import com.example.shopshoejavaspring.dto.cart.UpdateCartDTO;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.mapper.CartItemMapper;
import com.example.shopshoejavaspring.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ProductQuantityRepository productQuantityRepository;

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

        Product product = productRepository.findById(addCartItemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existCartItemOpt = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(addCartItemDTO.getProductId())
                        && item.getColorId().equals(addCartItemDTO.getColorId())
                        && item.getSizeId().equals(addCartItemDTO.getSizeId()))
                .findFirst();

        ProductQuantity productQuantity = productQuantityRepository.getProductQuantity(addCartItemDTO.getProductId(), addCartItemDTO.getColorId(), addCartItemDTO.getSizeId());

        if (productQuantity == null || productQuantity.getQuantity() < addCartItemDTO.getQuantity()) {
            throw new RuntimeException("Product quantity not enough");
        }

        if (existCartItemOpt.isPresent()) {
            CartItem existCartItem = existCartItemOpt.get();
            existCartItem.setQuantity(existCartItem.getQuantity() + addCartItemDTO.getQuantity());
            cartItemRepository.save(existCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(addCartItemDTO.getQuantity());
            cartItem.setColorId(addCartItemDTO.getColorId());
            cartItem.setSizeId(addCartItemDTO.getSizeId());
            productQuantity.setQuantity(productQuantity.getQuantity() - addCartItemDTO.getQuantity());
            cartItemRepository.save(cartItem);
        }
        return addCartItemDTO;
    }

    public void removeFromCart(RemoveFromCartDTO removeFromCartDTO) {

        // This is the original code
        // CartItem cartItem = cartItemRepository.findById(removeFromCartDTO.getCartItemId())
        //         .orElseThrow(() -> new RuntimeException("Cart item not found"));
        // cartItemRepository.delete(cartItem);

        // This is the modified code
        CartItem cartItem = cartItemRepository.findById(removeFromCartDTO.getCartItemId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        ProductQuantity productQuantity = productQuantityRepository.getProductQuantity(cartItem.getProduct().getId(), cartItem.getColorId(), cartItem.getSizeId());
        productQuantity.setQuantity(productQuantity.getQuantity() + cartItem.getQuantity());
        cartItemRepository.delete(cartItem);

//        cartItemRepository.deleteById(removeFromCartDTO.getCartItemId());
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

    public UpdateCartDTO updateProductCart(UpdateCartDTO updateCartDTO) {
        CartItem cartItem = cartItemRepository.findById(updateCartDTO.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        ProductQuantity productQuantity = productQuantityRepository.getProductQuantity(updateCartDTO.getProductId(), cartItem.getColorId(), cartItem.getSizeId());
        if (productQuantity.getQuantity() == 0 && updateCartDTO.getQuantity() > cartItem.getQuantity()) {
            throw new RuntimeException("Số lượng sản phẩm trong kho không đủ");
        }

        if (updateCartDTO.getQuantity() < cartItem.getQuantity()) {
            productQuantity.setQuantity(productQuantity.getQuantity() + cartItem.getQuantity() - updateCartDTO.getQuantity());
        } else {
            productQuantity.setQuantity(productQuantity.getQuantity() - updateCartDTO.getQuantity() + cartItem.getQuantity());
        }

        cartItem.setQuantity(updateCartDTO.getQuantity());
        cartItemRepository.save(cartItem);
        return updateCartDTO;
    }
}

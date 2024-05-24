package com.example.shopshoejavaspring.resource;

import com.example.shopshoejavaspring.dto.cart.*;
import com.example.shopshoejavaspring.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
public class CartResource {

    private final CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<AddCartItemDTO> addToCart(@RequestBody AddCartItemDTO addCartItemDTO) {
        log.info("REST request to add to cart : {}", addCartItemDTO);
        AddCartItemDTO result = cartService.addToCart(addCartItemDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/update-cart")
    public ResponseEntity<UpdateCartDTO> updateProductCart(@RequestBody UpdateCartDTO updateCartDTO) {
        log.info("REST request to update product cart : {}", updateCartDTO);
        UpdateCartDTO result = cartService.updateProductCart(updateCartDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody RemoveFromCartDTO removeFromCartDTO) {
        log.info("REST request to remove from cart : {}", removeFromCartDTO);
        cartService.removeFromCart(removeFromCartDTO);
        return ResponseEntity.ok().body("Remove from cart success");
    }

    @GetMapping("/get-cart-by-user-id/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartByUserId(@PathVariable Long userId) {
        log.info("REST request to get cart by user id : {}", userId);
        List<CartItemDTO> cartItemDTO = cartService.getCartByUserId(userId);
        return ResponseEntity.ok().body(cartItemDTO);
    }

    @GetMapping("/count-cart/{userId}")
    public ResponseEntity<Integer> getCountCart(@PathVariable Long userId) {
        log.info("REST request to get count cart by user id : {}", userId);
        Integer countCart = cartService.getCountCart(userId);
        return ResponseEntity.ok().body(countCart);
    }

    @DeleteMapping("/clear-cart/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        log.info("REST request to clear cart by user id : {}", userId);
        cartService.clearCart(userId);
        return ResponseEntity.ok().body("Clear cart success");
    }
}

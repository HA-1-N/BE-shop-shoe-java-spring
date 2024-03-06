package com.example.shopshoejavaspring.dto.cart;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.dto.size.SizeDTO;
import com.example.shopshoejavaspring.entity.CartItem;
import lombok.Data;

@Data
public class CartItemDTO {

        private Long id;

        private ProductDTO product;

        private Long cartId;

        private Long quantity;

        private ColorDTO color;

        private SizeDTO size;

}

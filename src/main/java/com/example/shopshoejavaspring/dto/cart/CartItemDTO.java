package com.example.shopshoejavaspring.dto.cart;

import com.example.shopshoejavaspring.dto.productImage.ProductImageDTO;
import com.example.shopshoejavaspring.entity.Color;
import com.example.shopshoejavaspring.entity.Product;
import com.example.shopshoejavaspring.entity.ProductImage;
import com.example.shopshoejavaspring.entity.Size;
import lombok.Data;

@Data
public class CartItemDTO {

        private Long id;

        private Product product;

        private Long cartId;

        private Long quantity;

        private Color color;

        private Size size;

        private ProductImage productImage;

}

package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.color.ColorDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.dto.size.SizeDTO;
import lombok.Data;

@Data
public class OrderProductDTO {

        private Long id;

        private ProductDTO product;

        private SizeDTO size;

        private ColorDTO color;

        private Long quantity;

        private Long totalPrice;
}

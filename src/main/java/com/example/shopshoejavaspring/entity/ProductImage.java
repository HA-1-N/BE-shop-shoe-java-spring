package com.example.shopshoejavaspring.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne // nhiều ảnh thuộc về 1 sản phẩm
    @JoinColumn(name = "product_id")
    private Product product;

}

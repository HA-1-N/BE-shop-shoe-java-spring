package com.example.shopshoejavaspring.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name ="product_id")
    private Product product;

    @Column(name ="quantity")
    private Long quantity;

    @Column(name ="price")
    private Long price;

    @Column(name = "size_id")
    private Long sizeId;

    @Column(name = "color_id")
    private Long colorId;

    @Column(name ="created_at")
    private String createdAt;

    @Column(name ="updated_at")
    private String updatedAt;

    @Column(name ="modified_at")
    private String modifiedAt;

}

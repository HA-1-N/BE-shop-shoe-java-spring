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

    @Column(name ="product_id")
    private Long product_id;

    @Column(name ="quantity")
    private Long quantity;

    @Column(name ="price")
    private Long price;

    @Column(name = "size_id")
    private Long size_id;

    @Column(name = "color_id")
    private Long color_id;

    @Column(name ="created_at")
    private String created_at;

    @Column(name ="updated_at")
    private String updated_at;

    @Column(name ="modified_at")
    private String modified_at;

}

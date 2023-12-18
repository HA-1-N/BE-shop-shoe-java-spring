package com.example.shopshoejavaspring.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product_quantity_image")
public class ProductQuantityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_quantity_id")
    private ProductQuantity productQuantity;

}

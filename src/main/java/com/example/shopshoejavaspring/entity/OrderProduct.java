package com.example.shopshoejavaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", insertable = true, updatable = false)
    private Order order;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id", insertable = true, updatable = false)
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "size_id", insertable = true, updatable = false)
    private Size size;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "color_id", insertable = true, updatable = false)
    private Color color;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_price")
    private Long totalPrice;
}

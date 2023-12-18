package com.example.shopshoejavaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product_quantity")
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product = new Product();

    @ManyToOne
    @JoinColumn(name = "size_id", insertable = true, updatable = false)
    private Size size = new Size();

    @ManyToOne
    @JoinColumn(name = "color_id", insertable = true, updatable = false)
    private Color color = new Color();

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "status")
    private Long status;

    @OneToMany(mappedBy = "productQuantity", cascade = CascadeType.ALL) // 1 sản phẩm có nhiều ảnh
    @JsonIgnore
    private List<ProductQuantityImage> productQuantityImages = new ArrayList<>();
}

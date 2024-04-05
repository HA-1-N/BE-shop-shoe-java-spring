package com.example.shopshoejavaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product")
//@EqualsAndHashCode(exclude = {"productImages", "productSizes", "productColors", "orderDetails"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message="Name is required")
    private String name;

    @Column(name = "price")
    private Double price;

//    @Column(name = "quantity")
//    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Long status;

    @JsonIgnoreProperties(value = {"product", "category"}, allowGetters = true) // khi trả về json thì sẽ bỏ qua thuộc tính này
    @ManyToOne  // nhiều sản phẩm thuộc về 1 thương hiệu
    @JoinColumn(name = "category_id", insertable = true, updatable = false)
    private Category category = new Category();

    @JsonIgnoreProperties(value = {"product", "brand"}, allowGetters = true) // khi trả về json thì sẽ bỏ qua thuộc tính này
    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = true, updatable = false)
    private Brand brand = new Brand();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 1 sản phẩm có nhiều ảnh
    @JsonIgnore
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 1 sản phẩm có nhiều quantity
    @JsonIgnore
    private List<ProductQuantity> productQuantities = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "product_hot_category",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "hot_category_id", referencedColumnName = "id"))
    @BatchSize(size = 20)
    @ToString.Exclude
    private Set<HotCategory> hotCategories = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "order_products",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
    @BatchSize(size = 20)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 1 sản phẩm có nhiều quantity
//    @JsonIgnore
//    private List<ProductQuantity> productQuantities = new ArrayList<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // 1 sản phẩm có nhiều quantity
//    @JsonIgnore
//    private List<ProductSize> productSizes = new ArrayList<>();

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "color_id", insertable = true, updatable = false)
//    private Color color = new Color();
//
//    @JsonIgnoreProperties(value = {"product", "size"}, allowGetters = true)
//    @ManyToOne
//    @JoinColumn(name = "size_id", insertable = true, updatable = false)
//    private Size size = new Size();

}

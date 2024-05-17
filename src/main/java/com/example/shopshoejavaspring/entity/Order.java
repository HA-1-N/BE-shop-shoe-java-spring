package com.example.shopshoejavaspring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // orphanRemoval = true: khi xóa 1 order thì orderItem cũng bị xóa, không cần phải xóa thủ công, cascade = CascadeType.ALL: khi thêm 1 order thì orderItem cũng được thêm, không cần phải thêm thủ công, mappedBy = "order": orderItem có khóa ngoại là order_id, nó sẽ tham chiếu đến đây, nó sẽ không tạo ra bảng order_item mà nó sẽ thêm cột order_id vào bảng orders
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    @JsonIgnore
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_method_id", referencedColumnName = "id")
    @JsonIgnore
    private ShippingMethod shippingMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @JsonIgnore
    private UserPayment userPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_address_id", referencedColumnName = "id")
    @JsonIgnore
    private UserAddress userAddress;

    @Column(name = "order_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC+07:00")
    private Instant orderDate;

    @Column(name = "order_total")
    private Double orderTotal;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderProduct> orderProducts = new ArrayList<>();

}

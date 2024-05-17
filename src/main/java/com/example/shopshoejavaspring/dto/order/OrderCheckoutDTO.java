package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.product.ProductCheckoutDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderCheckoutDTO {

    private Long userId;

    private String name;

    private String address;

    private String city;

    private String country;

    private String phone;

    private String prefix;

    private String shippingMethod;

    private String paymentMethod;

    private String status;

    private String note;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//    private Instant orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Instant orderDate;

    private Double orderTotal;

    private List<ProductCheckoutDTO> productCheckouts;

//    private String cardName;
//
//    private String cardNumber;
//
//    private String cardExpiration;
//
//    private String cardCvv;
//
//    private String cardType;
//
//    private String cardMonth;
//
//    private String cardYear;

}

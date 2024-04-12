package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.product.ProductCheckoutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Date orderDate;

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

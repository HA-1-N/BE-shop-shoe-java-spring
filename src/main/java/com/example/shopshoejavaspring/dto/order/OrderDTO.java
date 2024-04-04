package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private OrderStatusDTO orderStatus;

    private ShippingMethodDTO shippingMethod;

//    private UserPaymentDTO userPayment;

//    private UserAddressDTO userAddress;

    private Date orderDate;

    private Double orderTotal;
}

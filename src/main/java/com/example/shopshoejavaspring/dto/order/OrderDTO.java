package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.dto.user.UserAddressDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserPaymentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private OrderStatusDTO orderStatus;

    private ShippingMethodDTO shippingMethod;

    private UserPaymentDTO userPayment;

    private UserAddressDTO userAddress;

    private Date orderDate;

    private Double orderTotal;

    private List<OrderProductDTO> orderProducts;
}

package com.example.shopshoejavaspring.dto.order;

import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.dto.shippingMethod.ShippingMethodDTO;
import com.example.shopshoejavaspring.dto.user.UserAddressDTO;
import com.example.shopshoejavaspring.dto.user.UserDTO;
import com.example.shopshoejavaspring.dto.user.UserPaymentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant orderDate;

    private Double orderTotal;

    private List<OrderProductDTO> orderProducts;
}

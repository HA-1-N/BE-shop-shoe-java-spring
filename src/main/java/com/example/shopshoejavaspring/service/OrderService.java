package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final UserPaymentRepository userPaymentRepository;

    private final UserAddressRepository userAddressRepository;

    private final ShippingMethodRepository shippingMethodRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final PaymentTypeRepository paymentTypeRepository;

    private final ProductRepository productRepository;

    public void checkout(OrderCheckoutDTO orderCheckoutDTO) {

        Order order = new Order();

        UserAddress userAddress = new UserAddress();
        userAddress.setUser(userRepository.findById(orderCheckoutDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        userAddress.setName(orderCheckoutDTO.getName());
        userAddress.setAddress(orderCheckoutDTO.getAddress());
        userAddress.setCity(orderCheckoutDTO.getCity());
        userAddress.setCountry(orderCheckoutDTO.getCountry());
        userAddress.setPhone(orderCheckoutDTO.getPhone());
        userAddress.setPrefix(orderCheckoutDTO.getPrefix());

        order.setUserAddress(userAddress);

        ShippingMethod shippingMethod = shippingMethodRepository.findShippingMethodByMethodContaining(orderCheckoutDTO.getShippingMethod());

        order.setShippingMethod(shippingMethod);

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByStatusContaining(orderCheckoutDTO.getStatus());

        order.setOrderStatus(orderStatus);

        PaymentType paymentType = paymentTypeRepository.findPaymentTypeByTypeContaining(orderCheckoutDTO.getPaymentMethod());

        UserPayment userPayment = new UserPayment();
        userPayment.setUser(userRepository.findById(orderCheckoutDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        userPayment.setPaymentType(paymentType);
//        userPayment.setExpiry(orderCheckoutDTO.getExpiry());

        userPaymentRepository.save(userPayment);

        order.setUserPayment(userPayment);
        userAddressRepository.save(userAddress);

        Set<Product> products = orderCheckoutDTO.getProductIds().stream()
                .map(productId -> productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found")))
                .collect(Collectors.toSet());

        order.setProducts(products);

        order.setOrderDate(orderCheckoutDTO.getOrderDate());
        order.setOrderTotal(orderCheckoutDTO.getOrderTotal());
        orderRepository.save(order);
    }

    public Order getOrderByUserId(Long userId) {
        Order order = orderRepository.findOrders(userId);
        return order;
    }
}

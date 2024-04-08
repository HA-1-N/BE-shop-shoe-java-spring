package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.dto.product.ProductCheckoutDTO;
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

    private final ProductQuantityRepository productQuantityRepository;

    private final OrderProductRepository orderProductRepository;

    public void checkout(OrderCheckoutDTO orderCheckoutDTO) {

        Order order = new Order();

        User user = userRepository.findById(orderCheckoutDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        UserAddress userAddress = new UserAddress();
        userAddress.setUser(user);
        userAddress.setName(orderCheckoutDTO.getName());
        userAddress.setAddress(orderCheckoutDTO.getAddress());
        userAddress.setCity(orderCheckoutDTO.getCity());
        userAddress.setCountry(orderCheckoutDTO.getCountry());
        userAddress.setPhone(orderCheckoutDTO.getPhone());
        userAddress.setPrefix(orderCheckoutDTO.getPrefix());

        order.setUserAddress(userAddress);

        order.setUser(user);

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

        order.setOrderDate(orderCheckoutDTO.getOrderDate());
        order.setOrderTotal(orderCheckoutDTO.getOrderTotal());

        orderRepository.save(order);

        for (ProductCheckoutDTO productCheckoutDTO : orderCheckoutDTO.getProductCheckouts()) {
            Product product = productRepository.findById(productCheckoutDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
            ProductQuantity productQuantity = productQuantityRepository.findBySizeIdAndColorIdAndProductId(productCheckoutDTO.getSizeId(), productCheckoutDTO.getColorId(), productCheckoutDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Product quantity not found"));

            // Kiểm tra xem số lượng đặt hàng có lớn hơn số lượng trong kho không
            if (productQuantity.getQuantity() < productCheckoutDTO.getQuantity()) {
                throw new RuntimeException("Not enough quantity for product: " + product.getName());
            }

            productQuantity.setQuantity(productQuantity.getQuantity() - productCheckoutDTO.getQuantity());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(productCheckoutDTO.getQuantity());
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setTotalPrice(productCheckoutDTO.getTotalPrice());

            orderProductRepository.save(orderProduct);
        }
    }

    public Order getOrderByUserId(Long userId) {
        Order order = orderRepository.findOrders(userId);
        return order;
    }
}

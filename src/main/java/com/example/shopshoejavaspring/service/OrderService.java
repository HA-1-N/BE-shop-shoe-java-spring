package com.example.shopshoejavaspring.service;

import com.example.shopshoejavaspring.dto.order.FilterOrderDTO;
import com.example.shopshoejavaspring.dto.order.OrderCheckoutDTO;
import com.example.shopshoejavaspring.dto.order.OrderDTO;
import com.example.shopshoejavaspring.dto.order.UpdateOrderDTO;
import com.example.shopshoejavaspring.dto.orderStatus.OrderStatusDTO;
import com.example.shopshoejavaspring.dto.product.ProductCheckoutDTO;
import com.example.shopshoejavaspring.entity.*;
import com.example.shopshoejavaspring.mapper.OrderMapper;
import com.example.shopshoejavaspring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final UserRepository userRepository;

    private final UserPaymentRepository userPaymentRepository;

    private final UserAddressRepository userAddressRepository;

    private final ShippingMethodRepository shippingMethodRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final PaymentTypeRepository paymentTypeRepository;

    private final ProductRepository productRepository;

    private final ProductQuantityRepository productQuantityRepository;

    private final OrderProductRepository orderProductRepository;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

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

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//        Instant instant = Instant.from(formatter.parse(orderCheckoutDTO.getOrderDate().toString()));
//        order.setOrderDate(instant);

//        String orderDateStr = orderCheckoutDTO.getOrderDate().toString();
//        Instant instant = Instant.parse(orderDateStr);
//        order.setOrderDate(instant);

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

            Color color = colorRepository.findById(productCheckoutDTO.getColorId()).orElseThrow(() -> new RuntimeException("Color not found"));
            Size size = sizeRepository.findById(productCheckoutDTO.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(productCheckoutDTO.getQuantity());
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setTotalPrice(productCheckoutDTO.getTotalPrice());
            orderProduct.setColor(color);
            orderProduct.setSize(size);

            orderProductRepository.save(orderProduct);
        }
    }

    public List<OrderDTO> getOrderByUserId(Long userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderDate");
        List<Order> orders = orderRepository.findByUserId(userId, sort);
        List<OrderDTO> orderDTOS = orderMapper.toOrderDTOs(orders);
        return orderDTOS;
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDTO orderDTO = orderMapper.toDto(order);
        return orderDTO;
    }

    public Page<OrderDTO> filter(FilterOrderDTO filterOrderDTO, Pageable pageable) {
        Page<Order> orders = orderRepository.filter(filterOrderDTO.getOrderDate(), pageable);
        List<OrderDTO> orderDTOS = orderMapper.toOrderDTOs(orders.getContent());
//        List<OrderDTO> orderDTOS = orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(orderDTOS, pageable, orders.getTotalElements());
    }

    public OrderDTO changeOrderStatus(UpdateOrderDTO updateOrderDTO) {
        Order order = orderRepository.findById(updateOrderDTO.getId()).orElseThrow(() -> new RuntimeException("Order not found"));
        OrderStatus orderStatus = orderStatusRepository.findById(updateOrderDTO.getStatusId()).orElseThrow(() -> new RuntimeException("Order status not found"));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public Long getTotalOrder() {
        return orderRepository.count();
    }

    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

}

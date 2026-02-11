package com.example.jwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwt.dto.*;
import com.example.jwt.entity.*;
import com.example.jwt.repository.*;
import com.example.jwt.util.SecurityUtil;

@Service
@Transactional   // 🔥 THIS FIXES EVERYTHING
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE ORDER (Checkout)
    public void placeOrder(List<OrderRequestItem> items) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow();

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setStatus("PENDING");

        List<OrderItem> orderItems = items.stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductName(i.getProductName());
            item.setPrice(i.getPrice());
            item.setQuantity(i.getQuantity());
            return item;
        }).toList();

        order.setItems(orderItems);
        orderRepository.save(order);
    }

    // GET MY ORDERS
    @Transactional(readOnly = true) // extra safe
    public List<OrderResponse> getMyOrders() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow();

        return orderRepository.findByUser(user).stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getItems().stream()
                                .map(i -> new OrderItemResponse(
                                        i.getProductName(),
                                        i.getPrice(),
                                        i.getQuantity()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    // UPDATE STATUS
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }
}

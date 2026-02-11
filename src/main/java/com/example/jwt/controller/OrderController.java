package com.example.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.jwt.dto.OrderRequestItem;
import com.example.jwt.dto.OrderResponse;
import com.example.jwt.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // PLACE ORDER AFTER CHECKOUT
    @PostMapping
    public String placeOrder(@RequestBody List<OrderRequestItem> items) {
        orderService.placeOrder(items);
        return "Order saved successfully";
    }

    // FETCH USER ORDERS
    @GetMapping
    public List<OrderResponse> myOrders() {
        return orderService.getMyOrders();
    }

    // UPDATE STATUS (ADMIN / STRIPE)
    @PutMapping("/{orderId}/status")
    public String updateStatus(@PathVariable Long orderId,
                               @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "Order status updated";
    }
}

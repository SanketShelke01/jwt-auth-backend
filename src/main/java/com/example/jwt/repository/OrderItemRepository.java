package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

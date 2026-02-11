package com.example.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.Order;
import com.example.jwt.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}

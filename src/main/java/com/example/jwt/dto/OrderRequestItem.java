package com.example.jwt.dto;

public class OrderRequestItem {

    private String productName;
    private Double price;
    private Integer quantity;

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

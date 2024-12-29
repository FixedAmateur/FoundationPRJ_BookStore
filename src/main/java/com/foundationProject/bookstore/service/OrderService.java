package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.response.OrderResponse;

public interface OrderService {
    OrderResponse getOrderByOrderId(Long orderId);

    OrderResponse getOrderByUserId(Long userId);
}

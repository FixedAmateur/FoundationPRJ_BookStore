package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.entity.Order;
import com.foundationProject.bookstore.repository.OrderRepository;
import com.foundationProject.bookstore.service.OrderService;
import com.foundationProject.bookstore.model.response.OrderResponse;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse getOrderByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderByUserId(Long userId) {
        Order order = orderRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Order", "userId", userId));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        return orderResponse;
    }

}

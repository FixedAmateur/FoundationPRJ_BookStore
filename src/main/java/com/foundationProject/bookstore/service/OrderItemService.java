package com.foundationProject.bookstore.service;


import com.foundationProject.bookstore.model.dto.OrderItemDto;
import com.foundationProject.bookstore.model.response.OrderItemResponse;
import com.foundationProject.bookstore.model.response.PageCustom;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {

    OrderItemResponse createOrderItem(OrderItemDto orderItemDto);

    OrderItemResponse updateOrderItem(Long id, OrderItemDto orderItemDto);

    String updateOrderItemStatus(Long id);

    String deleteOrderItem(Long id);

    OrderItemResponse getOrderItemById(Long id);

    PageCustom<OrderItemResponse> getOrderItemByOrderId(Long orderId, Pageable pageable);

    PageCustom<OrderItemResponse> getAllOrderItem(Pageable pageable);

    PageCustom<OrderItemResponse> getAllOrderedHistory(Long orderId, Pageable pageable);

    PageCustom<OrderItemResponse> getAllOrderedHistoryByUserId(Long userId, Pageable pageable);
}

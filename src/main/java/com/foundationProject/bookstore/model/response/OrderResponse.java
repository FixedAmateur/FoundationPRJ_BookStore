package com.foundationProject.bookstore.model.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private int quantity;
    private Long totalPrice;
    private boolean status;
    private Set<OrderItemResponse> orderItems;

}

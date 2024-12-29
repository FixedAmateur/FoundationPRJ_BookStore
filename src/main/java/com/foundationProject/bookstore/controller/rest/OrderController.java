package com.foundationProject.bookstore.controller.rest;

import com.foundationProject.bookstore.service.OrderService;
import com.foundationProject.bookstore.model.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get Order By Order Id", description = "Get Order By Order Id API")
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderByOrderId(@PathVariable("orderId") Long orderId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrderByOrderId(orderId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Order By User Id", description = "Get Order By User Id API")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable("userId") Long userId) {
        ApiResponse apiResponse = ApiResponse.success(orderService.getOrderByUserId(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



}

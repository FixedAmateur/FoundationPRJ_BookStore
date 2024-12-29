package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.dto.OrderItemDto;
import com.foundationProject.bookstore.model.response.OrderItemResponse;
import com.foundationProject.bookstore.model.response.PageCustom;
import com.foundationProject.bookstore.model.entity.Book;
import com.foundationProject.bookstore.model.entity.Order;
import com.foundationProject.bookstore.model.entity.OrderItem;
import com.foundationProject.bookstore.model.entity.User;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import com.foundationProject.bookstore.repository.BookRepository;
import com.foundationProject.bookstore.repository.OrderItemRepository;
import com.foundationProject.bookstore.repository.OrderRepository;
import com.foundationProject.bookstore.repository.UserRepository;
import com.foundationProject.bookstore.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    public OrderItemResponse createOrderItem(OrderItemDto orderItemDto){

        if (orderItemRepository.existsByOrderIdAndStatusAndBookId(orderItemDto.getBookId(), false, orderItemDto.getBookId())) {
            OrderItem orderItem = orderItemRepository.findByOrderIdAndStatusAndBookId(orderItemDto.getBookId(), false, orderItemDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "bookId", orderItemDto.getBookId()));
            orderItem.setQuantity(orderItem.getQuantity() + orderItemDto.getQuantity());
            orderItem.setTotalPrice(orderItem.getQuantity() * orderItem.getBook().getUnitPrice());
            return modelMapper.map(orderItemRepository.save(orderItem), OrderItemResponse.class);
        }

        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
        User user =  userRepository.findById(orderItemDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", orderItemDto.getUserId()));
        orderItem.setUser(user);
        Order order = orderRepository.findByUserId(orderItemDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Order", "userId", orderItemDto.getUserId()));
        orderItem.setOrder(order);
        Book book = bookRepository.findById(orderItemDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book", "id", orderItemDto.getBookId()));
        orderItem.setBook(book);
        orderItem.setTotalPrice(orderItem.getQuantity() * book.getUnitPrice());

        return modelMapper.map(orderItemRepository.save(orderItem), OrderItemResponse.class);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemDto orderItemDto){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setTotalPrice(orderItem.getQuantity() * orderItem.getBook().getUnitPrice());
        return modelMapper.map(orderItemRepository.save(orderItem), OrderItemResponse.class);
    }

    @Override
    public String updateOrderItemStatus(Long id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
        orderItem.setStatus(true);
        orderItemRepository.save(orderItem);
        return "OrderItem with id: " +id+ " was updated successfully";
    }

    @Override
    public String deleteOrderItem(Long id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
        orderItemRepository.delete(orderItem);
        return "OrderItem with id: " +id+ " was deleted successfully";
    }

    @Override
    public OrderItemResponse getOrderItemById(Long id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
        return modelMapper.map(orderItem, OrderItemResponse.class);
    }

    @Override
    public PageCustom<OrderItemResponse> getOrderItemByOrderId(Long orderId, Pageable pageable){
        Page<OrderItem> orderItem = orderItemRepository.findAllByOrderIdAndStatus(orderId,false,pageable);
         PageCustom<OrderItemResponse> pageCustom = PageCustom.<OrderItemResponse>builder()
                .pageNo(orderItem.getNumber() + 1)
                .pageSize(orderItem.getSize())
                .totalPages(orderItem.getTotalPages())
                .pageContent(orderItem.getContent().stream().map(orderItem1 -> modelMapper.map(orderItem1, OrderItemResponse.class)).toList())
                .build();
         return pageCustom;
    }

    @Override
    public PageCustom<OrderItemResponse> getAllOrderItem(Pageable pageable){
        Page<OrderItem> orderItem = orderItemRepository.findAll(pageable);
         PageCustom<OrderItemResponse> pageCustom = PageCustom.<OrderItemResponse>builder()
                .pageNo(orderItem.getNumber() + 1)
                .pageSize(orderItem.getSize())
                .totalPages(orderItem.getTotalPages())
                .pageContent(orderItem.getContent().stream().map(orderItem1 -> modelMapper.map(orderItem1, OrderItemResponse.class)).toList())
                .build();
         return pageCustom;
    }

    @Override
    public PageCustom<OrderItemResponse> getAllOrderedHistory(Long orderId, Pageable pageable){
        Page<OrderItem> orderItem = orderItemRepository.findAllByOrderIdAndStatus(orderId,true,pageable);
         PageCustom<OrderItemResponse> pageCustom = PageCustom.<OrderItemResponse>builder()
                .pageNo(orderItem.getNumber() + 1)
                .pageSize(orderItem.getSize())
                .totalPages(orderItem.getTotalPages())
                .pageContent(orderItem.getContent().stream().map(orderItem1 -> modelMapper.map(orderItem1, OrderItemResponse.class)).toList())
                .build();
         return pageCustom;
    }

    @Override
    public PageCustom<OrderItemResponse> getAllOrderedHistoryByUserId(Long userId,Pageable pageable){
        Page<OrderItem> orderItem = orderItemRepository.findAllByUserIdAndStatus(userId,true,pageable);
         PageCustom<OrderItemResponse> pageCustom = PageCustom.<OrderItemResponse>builder()
                .pageNo(orderItem.getNumber() + 1)
                .pageSize(orderItem.getSize())
                .totalPages(orderItem.getTotalPages())
                .pageContent(orderItem.getContent().stream().map(orderItem1 -> modelMapper.map(orderItem1, OrderItemResponse.class)).toList())
                .build();
         return pageCustom;
    }



}

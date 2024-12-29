package com.foundationProject.bookstore.service;


import com.foundationProject.bookstore.model.response.PageCustom;
import com.foundationProject.bookstore.model.response.UserResponse;
import com.foundationProject.bookstore.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse getUserById(Long userId);

    PageCustom<UserResponse> getAllUser(Pageable pageable);

    UserResponse updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);
}

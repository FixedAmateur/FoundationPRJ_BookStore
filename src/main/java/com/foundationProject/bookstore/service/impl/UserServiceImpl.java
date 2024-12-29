package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.entity.User;
import com.foundationProject.bookstore.repository.RoleRepository;
import com.foundationProject.bookstore.model.dto.UserDto;
import com.foundationProject.bookstore.model.response.PageCustom;
import com.foundationProject.bookstore.model.response.UserResponse;
import com.foundationProject.bookstore.exception.AppException;
import com.foundationProject.bookstore.exception.ErrorCode;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import com.foundationProject.bookstore.repository.UserRepository;
import com.foundationProject.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserResponse getUserById(Long userId) {

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            UserResponse userResponse =  modelMapper.map(user, UserResponse.class);
            return userResponse;

    }


    @Override
    public PageCustom<UserResponse> getAllUser(Pageable pageable) {

            Page<User> page = userRepository.findAll(pageable);
            PageCustom<UserResponse> pageCustom = PageCustom.<UserResponse>builder()
                    .pageNo(page.getNumber() + 1)
                    .pageSize(page.getSize())
                    .totalPages(page.getTotalPages())
                    .pageContent(page.getContent().stream().map(user->modelMapper.map(user, UserResponse.class)).toList())
                    .build();

            return pageCustom;

    }

    @Override
    public UserResponse updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!userDto.getUsername().equals(user.getUsername())){
            throw new AppException(ErrorCode.User_NAME_CAN_NOT_BE_CHANGED);
        }
        modelMapper.map(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
        return "User with id: " +userId+ " was deleted successfully";
    }
}

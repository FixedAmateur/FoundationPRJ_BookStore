package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.entity.Order;
import com.foundationProject.bookstore.model.entity.Role;
import com.foundationProject.bookstore.model.entity.User;
import com.foundationProject.bookstore.repository.OrderRepository;
import com.foundationProject.bookstore.repository.RoleRepository;
import com.foundationProject.bookstore.service.AuthenticationService;
import com.foundationProject.bookstore.service.JwtService;
import com.foundationProject.bookstore.model.dto.LoginDto;
import com.foundationProject.bookstore.model.dto.RegisterDto;
import com.foundationProject.bookstore.model.response.JwtAuthResponse;
import com.foundationProject.bookstore.model.response.UserResponse;
import com.foundationProject.bookstore.exception.AppException;
import com.foundationProject.bookstore.exception.ErrorCode;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import com.foundationProject.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final OrderRepository orderRepository;


    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "username", loginDto.getUsername()));
        if (user == null) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            ));
        } catch (Exception exception) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }

        String jwtToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24);
        String refreshToken = jwtService.generateToken(user,jwtService.getExpirationTime()*24*2);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }

    @Override
    public JwtAuthResponse refreshToken(String refreshToken) {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        String accessToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24) ;
        refreshToken = jwtService.generateToken(user, jwtService.getExpirationTime()*24*2);

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userResponse)
                .expiredTime(new Timestamp(System.currentTimeMillis() +jwtService.getExpirationTime()))
                .build();
    }



    @Override
    public String register(RegisterDto registerDto){
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("role", "role's name","ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);
        return "User registered successfully!.";
    }
}

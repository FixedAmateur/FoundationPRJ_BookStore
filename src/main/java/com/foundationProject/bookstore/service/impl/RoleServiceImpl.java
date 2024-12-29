package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.entity.Role;
import com.foundationProject.bookstore.repository.RoleRepository;
import com.foundationProject.bookstore.model.dto.RoleDto;
import com.foundationProject.bookstore.model.response.RoleResponse;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import com.foundationProject.bookstore.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole (RoleDto roleDto){
        Role role = modelMapper.map(roleDto, Role.class);
        return modelMapper.map(roleRepository.save(role),RoleResponse.class);
    }

    @Override
    public RoleResponse getRoleByRoleName(String roleName){
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleName));
        return modelMapper.map(role,RoleResponse.class);
    }

    @Override
    public RoleResponse getRoleById(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleId));
        return modelMapper.map(role,RoleResponse.class);
    }

    @Override
    public List<RoleResponse> getAllRole(){
        return roleRepository.findAll().stream()
                .map(result->modelMapper.map(result,RoleResponse.class)).toList();
    }

    @Override
    public String deleteRole(Long roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("role","role's name",roleId));
        roleRepository.delete(role);
        return "Role "+ roleId+" was delete successfully";
    }


}

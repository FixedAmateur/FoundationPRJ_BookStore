package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.response.RoleResponse;
import com.foundationProject.bookstore.model.dto.RoleDto;

import java.util.List;

public interface RoleService  {
    RoleResponse createRole (RoleDto roleDto);

    RoleResponse getRoleByRoleName(String roleName);

    RoleResponse getRoleById(Long roleId);

    List<RoleResponse> getAllRole();

    String deleteRole(Long roleId);
}

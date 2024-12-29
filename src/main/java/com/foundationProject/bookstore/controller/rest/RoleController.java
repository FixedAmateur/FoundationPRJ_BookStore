package com.foundationProject.bookstore.controller.rest;


import com.foundationProject.bookstore.model.response.ApiResponse;
import com.foundationProject.bookstore.service.RoleService;
import com.foundationProject.bookstore.model.dto.RoleDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {
    private final RoleService roleService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<ApiResponse> createRole (@Valid @RequestBody RoleDto roleDto){
        ApiResponse apiResponse = ApiResponse.success(roleService.createRole(roleDto));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/name/{roleName}")
    public ResponseEntity<ApiResponse> getRoleByName(@PathVariable("roleName") String roleName){
        ApiResponse apiResponse = ApiResponse.success(roleService.getRoleByRoleName(roleName));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable("roleId") Long roleId){
        ApiResponse apiResponse = ApiResponse.success(roleService.getRoleById(roleId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllRole(){
        ApiResponse apiResponse = ApiResponse.success(roleService.getAllRole());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse> deleteRoleByName(@PathVariable("roleId") Long roleId){
        ApiResponse apiResponse = ApiResponse.success(roleService.deleteRole(roleId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
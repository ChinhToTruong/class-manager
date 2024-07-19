package com.zev.studentmanager.controller;

import com.zev.studentmanager.annotation.PageableParam;
import com.zev.studentmanager.dto.request.CreatePermissionRequest;
import com.zev.studentmanager.dto.request.CreateRoleRequest;
import com.zev.studentmanager.dto.request.UpdatePermissionRequest;
import com.zev.studentmanager.dto.request.UpdateRoleRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.service.PermissionService;
import com.zev.studentmanager.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/role")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Role Controller", description = "Need ADMIN access level")
@RestController
public class RoleController {
    private final RoleService roleService;


    @Operation(
            summary = "get all roles",
            description = "Api get all roles"
    )
    @GetMapping
    @PageableParam()
    public ResponseEntity<?> getAllRoles(@Parameter(hidden = true) Pageable pageable){
        return ApiResponse.build()
               .withSuccess(true)
               .withMessage(MessageCode.SUCCESS.getMessage())
               .withCode(MessageCode.SUCCESS.getCode())
               .withData(roleService.getRoles(pageable))
               .toEntity();
    }

    @Operation(
            summary = "get role by id",
            description = "Api get role by id",
            parameters = {@Parameter(name = "id", example = "1")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable(name = "id") @Min(1) Integer id) {
        return ApiResponse.build()
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withCode(MessageCode.SUCCESS.getCode())
                .withData(roleService.getRoleById(id))
                .toEntity();
    }

    @Operation(
            summary = "delete role by id",
            description = "Api delete role by id",
            parameters = {@Parameter(name = "id", example = "1")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermissionById(@PathVariable(name = "id") @Min(1) Integer id) {
        roleService.deleteRoleById(id);
        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }


    @Operation(
            summary = "add role",
            description = "Api add role"
    )
    @PostMapping
    public ResponseEntity<?> addPermission(@RequestBody @Valid CreateRoleRequest request) {
        roleService.addRole(request);

        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }

}

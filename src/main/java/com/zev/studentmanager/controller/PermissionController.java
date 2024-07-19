package com.zev.studentmanager.controller;

import com.zev.studentmanager.annotation.PageableParam;
import com.zev.studentmanager.dto.request.CreatePermissionRequest;
import com.zev.studentmanager.dto.request.UpdatePermissionRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/permission")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Permission Controller",  description = "Need ADMIN access level")
@RestController
public class PermissionController {
    private final PermissionService permissionService;


    @Operation(
            summary = "get all permissions",
            description = "Api get all permission"
    )
    @GetMapping
    @PageableParam()
    public ResponseEntity<?> getAllPermissions(@Parameter(hidden = true) Pageable pageable){
        return ApiResponse.build()
               .withSuccess(true)
               .withMessage(MessageCode.SUCCESS.getMessage())
               .withCode(MessageCode.SUCCESS.getCode())
               .withData(permissionService.getPermissions(pageable))
               .toEntity();
    }

    @Operation(
            summary = "get permission by id",
            description = "Api get permission by id",
            parameters = {@Parameter(name = "id", example = "1")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable(name = "id") @Min(1) Integer id) {
        return ApiResponse.build()
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withCode(MessageCode.SUCCESS.getCode())
                .withData(permissionService.getPermissionById(id))
                .toEntity();
    }

    @Operation(
            summary = "delete permission by id",
            description = "Api delete permission by id",
            parameters = {@Parameter(name = "id", example = "1")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermissionById(@PathVariable(name = "id") @Min(1) Integer id) {
        permissionService.deletePermission(id);
        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();
    }


    @Operation(
            summary = "add permission",
            description = "Api add permission"
    )
    @PostMapping()
    public ResponseEntity<?> addPermission(@RequestBody @Valid CreatePermissionRequest request) {
        permissionService.addPermission(request);

        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }

}

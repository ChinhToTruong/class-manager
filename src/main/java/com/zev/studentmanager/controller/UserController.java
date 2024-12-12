package com.zev.studentmanager.controller;

import com.zev.studentmanager.annotation.PageableParam;
import com.zev.studentmanager.dto.request.ChangePasswordRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/user")
@Log4j2
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
          summary = "get list user",
          description = "Api get list user"
    )
    @GetMapping
    @PageableParam
    public ResponseEntity<?> getUsers(@Parameter(hidden = true) Pageable pageable) {
        return ApiResponse.build()
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withData(userService.getUsers(pageable))
                .toEntity();
    }


    @Operation(
            summary = "update user",
            description = "Api update user information",
            parameters = {@Parameter(name = "id", description = "the id of the user",example = "1")}
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserInformation(
            @Valid @RequestBody UpdateUserInfoRequest request,
            @PathVariable(name = "id") @Min(1) Long id
    ){


        userService.updateUserInformation(request, id);
        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }

    @Operation(
            summary = "get active user",
            description = "Api get active user information"
    )
    @PageableParam
    @GetMapping("/active")
    public ResponseEntity<?> getActiveUser(@Parameter(hidden = true) Pageable pageable) {

        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withData(userService.getActiveUsers(pageable))
                .toEntity();
    }

    @Operation(
            summary ="soft-delete user",
            description = "Api soft-delete user information"
    )
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> deleteSoftUserById(@PathVariable(name = "id") @Min(1) Long id) {
        userService.deleteSoftUserById(id);
        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }


    @Operation(
            summary = "hard-delete user",
            description = "Api hard-delete user information"
    )
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") @Min(1) Long id) {
        userService.deleteUser(id);
        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }


    @Operation(
            summary = "get user by id",
            description = "Api get user information",
            parameters = {@Parameter(name = "id", description = "the id of the user",example = "1")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") @Min(1) Long id) {

        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withData(userService.getUserById(id))
                .toEntity();

    }

    @Operation(
            summary = "change password",
            description = "This API use to change password"
    )
    @PostMapping("/change-password/{id}")
    public ResponseEntity<?> refresh(@RequestBody @Valid ChangePasswordRequest request, Long id){
        userService.changePassword(request, id);

        return ApiResponse.build()
                .withSuccess(true)
                .withCode(MessageCode.SUCCESS.getCode())
                .toEntity();
    }

}

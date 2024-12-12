package com.zev.studentmanager.controller;

import com.zev.studentmanager.dto.request.RefreshTokenRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.AuthenticationRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RequestMapping("/authenticate")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Authentication Controller")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "login",
            description = "This API will return access token and refresh token"
    )
    @PostMapping("/access")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request){

        return ApiResponse.build()
                .withCode(MessageCode.SUCCESS.getCode())
                .withSuccess(true)
                .withData(authenticationService.authentication(request))
                .toEntity();
    }

    @Operation(
            summary = "register",
            description = "This API use to register new user"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws RoleNotFoundException {
        authenticationService.register(request);
        return ApiResponse.build()
                .withSuccess(true)
                .withCode(MessageCode.SUCCESS.getCode())
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();

    }

    @Operation(
            summary = "refresh",
            description = "This API use to refresh token"
    )
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshTokenRequest request){
        return ApiResponse.build()
                .withSuccess(true)
                .withCode(MessageCode.SUCCESS.getCode())
                .withMessage(MessageCode.SUCCESS.getMessage())
                .withData(authenticationService.refreshToken(request))
                .toEntity();
    }


    @Operation(
            summary = "logout",
            description = "This API use to logout"
    )
    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        authenticationService.logout();

        return ApiResponse.build()
                .withSuccess(true)
                .withCode(MessageCode.SUCCESS.getCode())
                .withMessage(MessageCode.SUCCESS.getMessage())
                .toEntity();
    }
}

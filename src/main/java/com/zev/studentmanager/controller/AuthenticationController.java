package com.zev.studentmanager.controller;

import com.zev.studentmanager.dto.request.RefreshTokenRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.SignInRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
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
    public ResponseEntity<?> login(@RequestBody @Valid SignInRequest request){
        log.info("------ get access token and refresh token ------");

        return ApiResponse.build()
                .withData(authenticationService.authentication(request))
                .toEntity();
    }

    @Operation(
            summary = "register",
            description = "This API use to register new user"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        log.info("------ register new user: {} ------", request.getUsername());
        try{
            authenticationService.register(request);
            return ApiResponse.build()
                    .withMessage("create user successfully")
                    .toEntity();

        }catch (Exception e){
            return ApiResponse.build()
                    .withCode(201)
                    .withSuccess(false)
                    .withMessage("create user failed")
                    .toEntity();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshTokenRequest request){
        return ApiResponse.build()
                .withData(authenticationService.refreshToken(request))
                .toEntity();
    }
}

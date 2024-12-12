package com.zev.studentmanager.controller;

import com.nimbusds.jose.JOSEException;
import com.zev.studentmanager.dto.request.*;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.dto.response.AuthenticationResponse;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authenticationService;

    @PostMapping("/token")
    ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.build().withData(result).toEntity();
    }

    @PostMapping("/introspect")
    ResponseEntity<?> authenticate(@RequestBody @Valid IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.build().withData(result).toEntity();
    }

    @PostMapping("/refresh")
    ResponseEntity<?> authenticate(@RequestBody @Valid RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.build().withData(result).toEntity();
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(@RequestBody @Valid LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.build().toEntity();
    }

}

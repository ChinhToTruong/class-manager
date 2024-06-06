package com.zev.studentmanager.service;

import com.zev.studentmanager.dto.request.ChangePasswordRequest;
import com.zev.studentmanager.dto.request.RefreshTokenRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.SignInRequest;
import com.zev.studentmanager.dto.response.TokenResponse;
import com.zev.studentmanager.dto.response.UserDto;

public interface AuthenticationService {
    TokenResponse authentication(SignInRequest request);
    void logout();
    TokenResponse refreshToken(RefreshTokenRequest request);
    void register(RegisterRequest request);

    void changePassword(ChangePasswordRequest request, Long userId);
}

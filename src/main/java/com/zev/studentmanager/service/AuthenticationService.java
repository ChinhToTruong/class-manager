package com.zev.studentmanager.service;

import com.zev.studentmanager.dto.request.RefreshTokenRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.AuthenticationRequest;
import com.zev.studentmanager.dto.response.TokenResponse;

import javax.management.relation.RoleNotFoundException;

public interface AuthenticationService {
    TokenResponse authentication(AuthenticationRequest request);
    void logout();
    TokenResponse refreshToken(RefreshTokenRequest request);
    void register(RegisterRequest request) throws RoleNotFoundException;

}

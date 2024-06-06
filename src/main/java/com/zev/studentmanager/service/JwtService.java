package com.zev.studentmanager.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    String extractUser(String token);

    boolean isValid(String token, UserDetails user);
}

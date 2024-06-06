package com.zev.studentmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class RefreshTokenRequest {
    @NotBlank(message = "The refresh token is required")
    private String token;
}

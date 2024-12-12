package com.zev.studentmanager.dto.request;


import com.zev.studentmanager.enums.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class AuthenticationRequest {

    @Schema(defaultValue = "admin")
    @NotBlank(message = "username must be not null")
    private String username;

    @Schema(defaultValue = "Admin123@")
    @NotBlank(message = "password must be not null")
    private String password;

    private Platform platform;

    private String deviceToken;

    private String version;
}

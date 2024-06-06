package com.zev.studentmanager.dto.request;


import com.zev.studentmanager.entity.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class SignInRequest {

    @NotBlank(message = "username must be not null")
    private String username;

    @NotBlank(message = "password must be not null")
    private String password;

    @NotNull(message = "platform must be not null")
    private Platform platform;

    private String deviceToken;

    private String version;
}

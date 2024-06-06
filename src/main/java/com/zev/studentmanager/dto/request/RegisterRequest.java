package com.zev.studentmanager.dto.request;

import com.zev.studentmanager.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @Size(min = 3, message = "Username must be at least 3 characters long")
    @NotBlank(message = "username cannot be null")
    String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[\\w!@#$%^&*(),.?\":{}|<>]{8,}$",
            message = "password must include one upper case letter, one number, one special character")
    @NotBlank(message = "password cannot be null")
    String password;

//    @NotNull(message = "role cannot be null")
//    Role role;
}

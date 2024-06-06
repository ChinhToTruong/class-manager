package com.zev.studentmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotBlank(message = "new password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[\\w!@#$%^&*(),.?\":{}|<>]{8,}$",
            message = "password must include one upper case letter, one number, one special character")
    private String newPassword;
}

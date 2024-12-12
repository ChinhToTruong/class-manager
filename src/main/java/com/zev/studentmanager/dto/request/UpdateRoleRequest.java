package com.zev.studentmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateRoleRequest {
    @Schema(defaultValue = "ADMIN")
    @NotBlank(message = "name must be required")
    private String name;

    @Schema(defaultValue = "admin level")
    @NotEmpty(message = "description must be required")
    private String description;
}

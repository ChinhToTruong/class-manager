package com.zev.studentmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePermissionRequest {
    @Schema(defaultValue = "ONLY_READ")
    @NotBlank(message = "name must be required")
    private String name;

    @Schema(defaultValue = "only read")
    @NotBlank(message = "description must be required")
    private String description;
}

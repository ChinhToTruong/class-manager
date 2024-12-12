package com.zev.studentmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePermissionRequest {
    @Schema(defaultValue = "ALL")
    @NotBlank(message = "name must be required")
    private String name;

    @Schema(defaultValue = "all levels access")
    @NotBlank(message = "description must be required")
    private String description;
}

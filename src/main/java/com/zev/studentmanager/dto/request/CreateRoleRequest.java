package com.zev.studentmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateRoleRequest {
    @Schema(defaultValue = "USER")
    @NotBlank(message = "name must be required")
    private String name;

    @Schema(defaultValue = "user level")
    @NotBlank(message = "description must be required")
    private String description;

    @Schema(defaultValue = "[1]")
    @NotEmpty(message = "permission must be required")
    private Set<Integer> permissions;
}

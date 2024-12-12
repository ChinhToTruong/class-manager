package com.zev.studentmanager.dto.response;

import com.zev.studentmanager.entity.Permission;
import com.zev.studentmanager.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class RoleDto {

    private Integer id;

    private String name;

    private String description;

    private Set<PermissionDto> permissions;
}

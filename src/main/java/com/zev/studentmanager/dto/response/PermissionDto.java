package com.zev.studentmanager.dto.response;

import com.zev.studentmanager.entity.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class PermissionDto {

    private Integer id;

    private String name;

    private String description;

}

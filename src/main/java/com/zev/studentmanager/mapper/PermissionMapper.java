package com.zev.studentmanager.mapper;

import com.zev.studentmanager.dto.response.PermissionDto;
import com.zev.studentmanager.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class,
        uses = {PermissionMapper.class}
)
public interface PermissionMapper extends EntityMapper<Permission, PermissionDto>{
}
